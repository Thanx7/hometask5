package com.epam.gomel.tat.test;

import java.io.File;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class YandexDiskTests {
    private static final String YANDEX_DISK = "https://disk.yandex.ru/";
    private static final String DOWNLOADS_PATH = "d:/";
    private static final String FILE_CONTAINS = "*";

    private static final By ATTACH_BUTTON_LOCATOR = By.xpath("//input[@class='button__attach']");
    private static final By ATTACHMENT_LOCATOR = By.xpath("(//div[contains(@title, 'attachment.txt')])[1]");
    private static final By ATTACHMENT_LOCATOR2 = By.xpath("(//div[contains(@title, 'attachment2.txt')])[1]");
    private static final By FILE_TO_DOWNLOAD_LOCATOR = By.xpath("//button[@data-click-action='resource.download']");
    private static final By FILE_TO_DELETE_LOCATOR = By.xpath("//button[@data-click-action='resource.delete']");
    private static final By FILE_TO_RESTORE_LOCATOR = By.xpath("//button[@data-click-action='resource.restore']");
    private static final By TRASH_LOCATOR = By.xpath("//div[contains(@title, 'Корзина')]");

    private static final String CRAZY_LOCATOR = "//*[@id='nb-1']/body/div[1]/div/div[2]/div[1]/div"
                    + "/div[2]/div[2]/div/div[2]/div/div[2]/div[4]/button";
    private static final By GROUP_DELETE_LOCATOR = By.xpath(CRAZY_LOCATOR);

    private WebDriver driver;

    @BeforeMethod
    public void fxDriverSetup() {
        System.setProperty("webdriver.chrome.driver", Tests.PATH_TO_CHROME_DRIVER);
        String downloadFilePath = DOWNLOADS_PATH;
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadFilePath);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);

        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability(ChromeOptions.CAPABILITY, options);
        cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

        driver = new ChromeDriver(cap);

        driver.manage().timeouts().implicitlyWait(DriverTimeouts.IMPLICIT_WAIT.getValue(),
                        DriverTimeouts.IMPLICIT_WAIT.getUnit());
        driver.manage().timeouts().pageLoadTimeout(DriverTimeouts.PAGE_LOAD.getValue(),
                        DriverTimeouts.PAGE_LOAD.getUnit());
        driver.manage().window().maximize();
    }

    @Test
    public void yandexDiskSaveAttachment() {
        login();
        Boolean isPresent = driver.findElements(ATTACHMENT_LOCATOR).size() > 0;
        Assert.assertFalse(isPresent);
        driver.findElement(ATTACH_BUTTON_LOCATOR).sendKeys(new File(Tests.PATH_TO_ATTACHMENT).getAbsolutePath());

        new WebDriverWait(driver, Tests.MY_WAIT)
                        .until(ExpectedConditions.visibilityOfElementLocated(ATTACHMENT_LOCATOR));
        isPresent = driver.findElements(ATTACHMENT_LOCATOR).size() > 0;
        Assert.assertTrue(isPresent);
        waitSomeTime();
    }

    @Test(dependsOnMethods = {"yandexDiskSaveAttachment"})
    public void checkAttachment() {
        login();
        new WebDriverWait(driver, Tests.MY_WAIT)
                        .until(ExpectedConditions.visibilityOfElementLocated(ATTACHMENT_LOCATOR));
        WebElement attachment = driver.findElement(ATTACHMENT_LOCATOR);
        attachment.click();
        new WebDriverWait(driver, Tests.MY_WAIT)
                        .until(ExpectedConditions.visibilityOfElementLocated(FILE_TO_DOWNLOAD_LOCATOR));
        driver.findElement(FILE_TO_DOWNLOAD_LOCATOR).click();
        waitSomeTime();

        MyFileReader myFileReader = new MyFileReader();
        Assert.assertTrue(FILE_CONTAINS.equals(myFileReader.myReader()));
    }

    @Test(dependsOnMethods = {"checkAttachment"})
    public void trashTest() {
        login();
        // the uploaded file is correct, it was checked in yandexDiskSaveAttachment
        // it's time for deletion
        new WebDriverWait(driver, Tests.MY_WAIT)
                        .until(ExpectedConditions.visibilityOfElementLocated(ATTACHMENT_LOCATOR));
        WebElement attachment = driver.findElement(ATTACHMENT_LOCATOR);
        attachment.click();
        new WebDriverWait(driver, Tests.MY_WAIT)
                        .until(ExpectedConditions.visibilityOfElementLocated(FILE_TO_DELETE_LOCATOR));
        driver.findElement(FILE_TO_DELETE_LOCATOR).click();
        waitSomeTime();

        WebElement trash = driver.findElement(TRASH_LOCATOR);
        new Actions(driver).doubleClick(trash).build().perform();
        waitSomeTime();

        Boolean isPresent = driver.findElements(ATTACHMENT_LOCATOR).size() > 0;
        Assert.assertTrue(isPresent);

        attachment = driver.findElement(ATTACHMENT_LOCATOR);
        attachment.click();
        new WebDriverWait(driver, Tests.MY_WAIT)
                        .until(ExpectedConditions.visibilityOfElementLocated(FILE_TO_DELETE_LOCATOR));
        driver.findElement(FILE_TO_DELETE_LOCATOR).click();
        waitSomeTime();
    }

    @Test(dependsOnMethods = {"trashTest"})
    public void trashIsEmptyTest() {
        login();
        WebElement trash = driver.findElement(TRASH_LOCATOR);
        new Actions(driver).doubleClick(trash).build().perform();
        waitSomeTime();
        Boolean isDeletedFromTrash = driver.findElements(ATTACHMENT_LOCATOR).size() > 0;
        Assert.assertFalse(isDeletedFromTrash);
    }

    @Test(dependsOnMethods = {"trashIsEmptyTest"})
    public void uploadAndCheckForRestore() {
        login();
        Boolean isPresent = driver.findElements(ATTACHMENT_LOCATOR).size() > 0;
        Assert.assertFalse(isPresent);
        driver.findElement(ATTACH_BUTTON_LOCATOR).sendKeys(new File(Tests.PATH_TO_ATTACHMENT).getAbsolutePath());

        new WebDriverWait(driver, Tests.MY_WAIT)
                        .until(ExpectedConditions.visibilityOfElementLocated(ATTACHMENT_LOCATOR));
        isPresent = driver.findElements(ATTACHMENT_LOCATOR).size() > 0;
        Assert.assertTrue(isPresent);
        waitSomeTime();
    }

    @Test(dependsOnMethods = {"uploadAndCheckForRestore"})
    public void restoreTest() {
        login();
        // remove file
        new WebDriverWait(driver, Tests.MY_WAIT)
                        .until(ExpectedConditions.visibilityOfElementLocated(ATTACHMENT_LOCATOR));
        WebElement attachment = driver.findElement(ATTACHMENT_LOCATOR);
        attachment.click();
        new WebDriverWait(driver, Tests.MY_WAIT)
                        .until(ExpectedConditions.visibilityOfElementLocated(FILE_TO_DELETE_LOCATOR));
        driver.findElement(FILE_TO_DELETE_LOCATOR).click();
        waitSomeTime();

        WebElement trash = driver.findElement(TRASH_LOCATOR);
        new Actions(driver).doubleClick(trash).build().perform();
        waitSomeTime();

        // check file is in trash
        Boolean isPresentInTrash = driver.findElements(ATTACHMENT_LOCATOR).size() > 0;
        Assert.assertTrue(isPresentInTrash);

        // restore file
        attachment = driver.findElement(ATTACHMENT_LOCATOR);
        attachment.click();
        new WebDriverWait(driver, Tests.MY_WAIT)
                        .until(ExpectedConditions.visibilityOfElementLocated(FILE_TO_RESTORE_LOCATOR));
        driver.findElement(FILE_TO_RESTORE_LOCATOR).click();
        waitSomeTime();
    }

    @Test(dependsOnMethods = {"restoreTest"})
    public void restoredTest() {
        login();
        Boolean restored = driver.findElements(ATTACHMENT_LOCATOR).size() > 0;
        Assert.assertTrue(restored);
    }

    @Test(dependsOnMethods = {"restoredTest"})
    public void addOneMoreFileTest() {
        login();
        // add one more file
        driver.findElement(ATTACH_BUTTON_LOCATOR).sendKeys(new File(Tests.PATH_TO_ATTACHMENT2).getAbsolutePath());

        new WebDriverWait(driver, Tests.MY_WAIT)
                        .until(ExpectedConditions.visibilityOfElementLocated(ATTACHMENT_LOCATOR2));
        Boolean isPresent = driver.findElements(ATTACHMENT_LOCATOR2).size() > 0;
        Assert.assertTrue(isPresent);
        waitSomeTime();
    }

    @Test(dependsOnMethods = {"addOneMoreFileTest"})
    public void removeSeveralElementsAtOnceTest() {
        login();

        // wait both elements
        new WebDriverWait(driver, Tests.MY_WAIT)
                        .until(ExpectedConditions.visibilityOfElementLocated(ATTACHMENT_LOCATOR));
        new WebDriverWait(driver, Tests.MY_WAIT)
                        .until(ExpectedConditions.visibilityOfElementLocated(ATTACHMENT_LOCATOR2));

        // click both
        WebElement attachment1 = driver.findElement(ATTACHMENT_LOCATOR);
        WebElement attachment2 = driver.findElement(ATTACHMENT_LOCATOR2);

        Actions actions = new Actions(driver);
        actions.keyDown(Keys.LEFT_CONTROL).click(attachment1).click(attachment2).keyUp(Keys.LEFT_CONTROL).build()
                        .perform();

        new WebDriverWait(driver, Tests.MY_WAIT)
                        .until(ExpectedConditions.visibilityOfElementLocated(GROUP_DELETE_LOCATOR));
        driver.findElement(GROUP_DELETE_LOCATOR).click();
        waitSomeTime();

        WebElement trash = driver.findElement(TRASH_LOCATOR);
        new Actions(driver).doubleClick(trash).build().perform();
        waitSomeTime();

        // check file is in trash
        Boolean isPresentInTrash1 = driver.findElements(ATTACHMENT_LOCATOR).size() > 0;
        Boolean isPresentInTrash2 = driver.findElements(ATTACHMENT_LOCATOR2).size() > 0;
        Assert.assertTrue(isPresentInTrash1 && isPresentInTrash2);

    }

    @AfterMethod
    private void killDriver() {
        driver.quit();
    }

    private void login() {
        driver.get(YANDEX_DISK);
        BaseLoginPage baseLoginPage = PageFactory.initElements(driver, BaseLoginPage.class);
        baseLoginPage.userYandexDiskLogin();
    }

    protected void waitSomeTime() {
        try {
            Thread.sleep(Tests.MY_WAIT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
