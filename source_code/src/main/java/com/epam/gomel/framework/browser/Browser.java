package com.epam.gomel.framework.browser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.epam.gomel.DriverTimeouts;
import com.epam.gomel.framework.config.GlobalConfig;
import com.epam.gomel.framework.report.Logger;
import com.epam.gomel.framework.util.GenerateDataUtils;
import com.epam.gomel.lib.common.Constants;

public final class Browser {
    private static final int PAGE_LOAD_DEFAULT_TIMEOUT_SECONDS = 20;
    private static final int COMMAND_DEFAULT_TIMEOUT_SECONDS = 15;
    private static final int WAIT_ELEMENT_TIMEOUT = 20;

    private static Browser instance;

    private WebDriver driver;

    private Browser(WebDriver driver) {
        this.driver = driver;
    }

    public static Browser current() {
        if (instance != null) {
            return instance;
        }
        return instance = init();
    }

    public WebDriver getWrappedDriver() {
        return driver;
    }

    private static Browser init() {
        WebDriver driver = null;
        if (GlobalConfig.getInstance().getBrowserType() == BrowserType.FIREFOX) {
            Logger.info("Initialising firefox browser.");
            driver = getFireFoxDriver();
        } else if (GlobalConfig.getInstance().getBrowserType() == BrowserType.CHROME) {
            Logger.info("Initialising chrome browser.");
            driver = getChromeDriver();
        }

        driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(COMMAND_DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return new Browser(driver);
    }

    private static WebDriver getFireFoxDriver() {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        profile.setPreference("browser.download.dir", GenerateDataUtils.getDownloadDirectoryPath());
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/plain");
        if (GlobalConfig.getInstance().getSeleniumHub().equals("")
                        || GlobalConfig.getInstance().getSeleniumHub() == null) {
            return new FirefoxDriver(profile);
        } else {
            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            capabilities.setCapability(FirefoxDriver.PROFILE, profile);
            try {
                return new RemoteWebDriver(new URL(GlobalConfig.getInstance().getSeleniumHub()), capabilities);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return new FirefoxDriver(profile);
            }
        }
    }

    private static WebDriver getChromeDriver() {
        System.setProperty("webdriver.chrome.driver", Constants.PATH_TO_CHROME_DRIVER);
        String downloadFilePath = GenerateDataUtils.getDownloadDirectoryPath();
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadFilePath);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);

        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability(ChromeOptions.CAPABILITY, options);
        cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

        WebDriver driver = null;
        driver = new ChromeDriver(cap);

        driver.manage().timeouts().implicitlyWait(DriverTimeouts.IMPLICIT_WAIT.getValue(),
                        DriverTimeouts.IMPLICIT_WAIT.getUnit());
        driver.manage().timeouts().pageLoadTimeout(DriverTimeouts.PAGE_LOAD.getValue(),
                        DriverTimeouts.PAGE_LOAD.getUnit());
        driver.manage().window().maximize();
        return driver;
    }

    public void open(String url) {
        Logger.info("Going to URL: " + url);
        driver.get(url);
    }

    public static void kill() {
        if (instance != null) {
            try {
                instance.driver.quit();
            } finally {
                instance = null;
            }
        }
    }

    public void waitForElementPresent(By locator, boolean isIframe) {
        if (isIframe) {
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe")));
        }
        new WebDriverWait(driver, WAIT_ELEMENT_TIMEOUT)
                        .until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public void waitForElementPresent(By locator) {
        new WebDriverWait(driver, WAIT_ELEMENT_TIMEOUT)
                        .until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public void waitForElementEnabled(By locator) {
        new WebDriverWait(driver, WAIT_ELEMENT_TIMEOUT).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void waitForElementVisible(By locator) {
        new WebDriverWait(driver, WAIT_ELEMENT_TIMEOUT)
                        .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public void highlightElement(By locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='5px solid green'",
                        driver.findElement(locator));
    }

    public void unHighlightElement(By locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='0px'", driver.findElement(locator));
    }

    public void click(final By locator) {
        waitForElementPresent(locator);
        waitForElementVisible(locator);
        waitForElementEnabled(locator);
        highlightElement(locator);
        Logger.info("Clicking element '" + driver.findElement(locator).getText() + "' (Located: " + locator + ")");
        takeScreenshot();
        unHighlightElement(locator);
        driver.findElement(locator).click();
    }

    public void type(final By locator, String text, boolean isIframe) {
        waitForElementPresent(locator, isIframe);
        waitForElementVisible(locator);
        waitForElementEnabled(locator);
        highlightElement(locator);
        Logger.info("Typing text '" + text + "' to input form '" + driver.findElement(locator).getAttribute("name")
                        + "' (Located: " + locator + ")");
        driver.findElement(locator).sendKeys(text);
        takeScreenshot();
        unHighlightElement(locator);
    }

    public void type(final By locator, String text) {
        waitForElementPresent(locator);
        waitForElementVisible(locator);
        waitForElementEnabled(locator);
        highlightElement(locator);
        Logger.info("Typing text '" + text + "' to input form '" + driver.findElement(locator).getAttribute("name")
                        + "' (Located: " + locator + ")");
        driver.findElement(locator).sendKeys(text);
        takeScreenshot();
        unHighlightElement(locator);
    }

    public void type(final By locator, String path, String attachment) {
        waitForElementPresent(locator);
        waitForElementVisible(locator);
        waitForElementEnabled(locator);
        highlightElement(locator);
        Logger.info("Path '" + path + "attachment '" + attachment + driver.findElement(locator).getAttribute("name")
                        + "' (Located: " + locator + ")");

        Actions actions = new Actions(driver);
        WebElement w = driver.findElement(locator);
        actions.moveToElement(w).click().perform();
        driver.switchTo().activeElement().sendKeys(new File(path + attachment).getAbsolutePath());

        takeScreenshot();
        unHighlightElement(locator);
    }

    public void typeInUploadInput(final By locator, String text) {
        highlightElement(locator);
        Logger.info("Typing text '" + text + "' to input form '" + driver.findElement(locator).getAttribute("name")
                        + "' (Located: " + locator + ")");
        driver.findElement(locator).sendKeys(text);
        takeScreenshot();
        driver.findElement(locator).sendKeys(text);
    }

    public String read(final By locator) {
        waitForElementPresent(locator);
        waitForElementVisible(locator);
        waitForElementEnabled(locator);
        highlightElement(locator);
        Logger.info("Reading text: " + driver.findElement(locator).getText());
        takeScreenshot();
        unHighlightElement(locator);
        return driver.findElement(locator).getText();
    }

    public void dragAndDrop(final By locator, final By targetLocator) {
        waitForElementPresent(locator);
        waitForElementVisible(locator);
        waitForElementEnabled(locator);
        waitForElementPresent(targetLocator);
        waitForElementVisible(targetLocator);
        waitForElementEnabled(targetLocator);
        WebElement element = driver.findElement(locator);
        WebElement target = driver.findElement(targetLocator);
        highlightElement(locator);
        highlightElement(targetLocator);
        takeScreenshot();
        Logger.info("Dragging element '" + driver.findElement(locator).getText() + "' (Located: " + locator + ")"
                        + "to '" + driver.findElement(targetLocator).getText() + "' (Located: " + targetLocator + ")");
        (new Actions(driver)).dragAndDrop(element, target).perform();
        unHighlightElement(targetLocator);
    }

    public By selectSeveralElements(List<By> locators) {
        Actions action = new Actions(driver);
        action.keyDown(Keys.CONTROL);
        WebElement element;
        for (By locator : locators) {
            waitForElementPresent(locator);
            waitForElementVisible(locator);
            waitForElementEnabled(locator);
            highlightElement(locator);
            Logger.info("Clicking element '" + driver.findElement(locator).getText() + "' (Located: " + locator + ")");
            element = driver.findElement(locator);
            action.moveToElement(element).click();
        }

        takeScreenshot();
        action.keyUp(Keys.CONTROL).perform();
        return locators.get(0);
    }

    public boolean isDisplayed(By locator) {
        waitForElementPresent(locator);
        boolean succeed = driver.findElements(locator).size() > 0;
        if (succeed) {
            Logger.info("Element " + driver.findElement(locator).getText() + " is present.");
            highlightElement(locator);
            takeScreenshot();
            unHighlightElement(locator);
        } else {
            Logger.error("Element " + driver.findElement(locator).getText() + " is not present.");
        }
        return succeed;
    }

    public void refresh() {
        driver.navigate().refresh();
    }

    public void takeScreenshot() {
        try {
            byte[] screenshotBytes =
                            ((TakesScreenshot) Browser.current().getWrappedDriver()).getScreenshotAs(OutputType.BYTES);
            File screenshotFile = new File(screenshotName());
            FileUtils.writeByteArrayToFile(screenshotFile, screenshotBytes);
            Logger.save(screenshotFile.getName());
        } catch (IOException e) {
            Logger.error("Failed to write screenshot: " + e.getMessage(), e);
        }
    }

    private static String screenshotName() {
        return GlobalConfig.getInstance().getResultDir() + File.separator + System.nanoTime() + ".png";
    }
}
