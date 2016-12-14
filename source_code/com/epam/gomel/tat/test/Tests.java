package com.epam.gomel.tat.test;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Tests {
    public static final String EMAIL = "shura.dubeykovskiy@mail.ru";
    public static final String CORRECT_PASS = "thankyou777";
    public static final String PATH_TO_ATTACHMENT = "./src/main/resources/attachment.txt";
    public static final String PATH_TO_ATTACHMENT2 = "./src/main/resources/attachment2.txt";
    public static final String PATH_TO_CHROME_DRIVER = "./src/main/resources/drivers/chromedriver.exe";

    protected static final int MY_WAIT = 2000;

    private static final String MAIL_RU = "https://e.mail.ru/login/";
    private static final String EMAIL_WITHOUT_PROPERLY_FILLED_ADDRESS = "elephant";
    private static final String WRONG_EMAIL = "wrong.dubeykovskiy@mail.ru";
    private static final String TOPIC_TEXT = "test selenium.WebDriver";
    private static final String TOPIC_TEXT_FOR_EMAIL_WITHOUT_PFA = "test with wrong e-mail";
    private static final String TEXT_FIELD_TEXT = "test";
    private static final String NO_SUBJECT = "<";
    private static final String INNER_TEXT = "innerText";
    private static final int ONE_SECOND = 1;

    private static final By LOGIN_INPUT_LOCATOR = By.xpath("//input[@name='Username']");
    private static final By PASSWORD_INPUT_LOCATOR = By.xpath("//input[@name='Password']");
    private static final By LOGIN_FORM_SUBMIT_BUTTON_LOCATOR = By.xpath("(//button[@data-name='submit'])[1]");
    private static final By USER_NAME_LABEL_LOCATOR = By.xpath("//*[@id='PH_user-email']");
    private static final By WRITE_LETTER_LOCATOR = By.xpath("//a[@class='b-toolbar__btn js-shortcut']"
                    + "//span[@class='b-toolbar__btn__text b-toolbar__btn__text_pad']");
    private static final By DRAFT_LETTER_LOCATOR =
                    By.xpath("//a[@href='/messages/drafts/']//span[@class='b-nav__item__text']");
    private static final By SAVE_DRAFT_LOCATOR = By.xpath("//div[@data-name='saveDraft']//span");
    private static final By DRAFT_SAVED_LOCATOR = By.xpath("//a[@class='toolbar__message_info__link']");
    private static final By ADDRESS_LOCATOR = By.xpath("//textarea[@data-original-name='To']");
    private static final By TOPIC_LOCATOR = By.xpath("//input[@class='b-input']");
    private static final By TEXT_FIELD_LOCATOR = By.xpath("//span[@class='mceEditor defaultSkin']//iframe");
    private static final By SEND_BUTTON_LOCATOR =
                    By.xpath("//div[@data-name='send']//span[@class='b-toolbar__btn__text']");
    private static final By WARNING_ABOUT_EMPTY_LETTER_LOCATOR = By.xpath("//div[@class='is-compose-empty_in']"
                    + "//button[@class='btn btn_stylish btn_main confirm-ok']//span");
    private static final By INBOX_LOCATOR = By.xpath("//*[@class='b-nav__item__text b-nav__item__text_unread']");
    private static final By LETTER_LOCATOR =
                    By.cssSelector("div.b-datalist__item:first-child div.b-datalist__item__subj");
    private static final By SEND_FOLDER_LOCATOR =
                    By.xpath("//a[@href='/messages/sent/']//span[@class='b-nav__item__text']");
    private static final By IFRAME_LOCATOR = By.xpath("//iframe[starts-with(@src, 'https://account.mail.ru')]");
    private static final By CBOX_LOCATOR = By.cssSelector("div.b-datalist__item:first-child div.b-checkbox__box");
    private static final By TRASH_FOLDER_LOCATOR =
                    By.xpath("//a[@href='/messages/trash/']//span[@class='b-nav__item__text']");
    private static final By DELETE_DESIGN_LOCATOR =
                    By.xpath("(//div[@class='compose__editor']//span[@class='mceToolbarLinkTitle'])[2]");
    private static final By TEXT_AREA_LOCATOR = By.xpath("//textarea[starts-with(@id, 'toolkit')]");
    private static final By ATTACH_FILE_LOCATOR = By.xpath("//input[@class='compose-attachments__input js-shortcut']");

    private WebDriver driver;

    @BeforeMethod
    @Parameters("browser")
    protected void setup(String browser) throws Exception {
        if ("firefox".equalsIgnoreCase(browser)) {
            driver = new FirefoxDriver();
        } else if ("chrome".equalsIgnoreCase(browser)) {
            System.setProperty("webdriver.chrome.driver", PATH_TO_CHROME_DRIVER);
            driver = new ChromeDriver();
        }
        driver.manage().timeouts().implicitlyWait(DriverTimeouts.IMPLICIT_WAIT.getValue(),
                        DriverTimeouts.IMPLICIT_WAIT.getUnit());
        driver.manage().timeouts().pageLoadTimeout(DriverTimeouts.PAGE_LOAD.getValue(),
                        DriverTimeouts.PAGE_LOAD.getUnit());
        driver.manage().window().maximize();
    }

    @Test
    @Parameters("browser")
    public void loginTest(String browser) throws Exception {
        login();
        Assert.assertEquals(driver.findElement(USER_NAME_LABEL_LOCATOR).getText(), EMAIL);
        // send e-mail
        driver.findElement(WRITE_LETTER_LOCATOR).click();
        driver.findElement(ADDRESS_LOCATOR).sendKeys(EMAIL);
        driver.findElement(TOPIC_LOCATOR).sendKeys(TOPIC_TEXT);
        fillEmailTextArea(browser);
        driver.findElement(ATTACH_FILE_LOCATOR).sendKeys(new File(PATH_TO_ATTACHMENT).getAbsolutePath());
        waitSomeTime();
        driver.findElement(SEND_BUTTON_LOCATOR).click();
    }

    @Test(dependsOnMethods = {"loginTest"})
    public void wrongLoginTest() {
        driver.get(MAIL_RU);
        WebElement iframe = driver.findElement(IFRAME_LOCATOR);
        driver.switchTo().frame(iframe);
        driver.findElement(LOGIN_INPUT_LOCATOR).sendKeys(WRONG_EMAIL);
        driver.findElement(PASSWORD_INPUT_LOCATOR).sendKeys(CORRECT_PASS);
        driver.findElement(LOGIN_FORM_SUBMIT_BUTTON_LOCATOR).click();
        Assert.assertNotEquals(driver.findElement(USER_NAME_LABEL_LOCATOR).getText(), WRONG_EMAIL);
    }

    @Test(dependsOnMethods = {"wrongLoginTest"})
    public void loginTestSendFolder() {
        // check if e-mail exists in the Send folder
        login();
        driver.findElement(SEND_FOLDER_LOCATOR).click();
        waitSomeTime();
        driver.manage().timeouts().implicitlyWait(ONE_SECOND, TimeUnit.SECONDS);
        Assert.assertEquals(
                        driver.findElement(LETTER_LOCATOR).getAttribute(INNER_TEXT).substring(0, TOPIC_TEXT.length()),
                        TOPIC_TEXT);
    }

    @Test(dependsOnMethods = {"loginTestSendFolder"})
    public void loginTestInboxFolder() {
        login();
        // check if e-mail exists in the Inbox folder
        driver.findElement(INBOX_LOCATOR).click();
        waitSomeTime();
        driver.manage().timeouts().implicitlyWait(ONE_SECOND, TimeUnit.SECONDS);
        Assert.assertEquals(
                        driver.findElement(LETTER_LOCATOR).getAttribute(INNER_TEXT).substring(0, TOPIC_TEXT.length()),
                        TOPIC_TEXT);
    }

    @Test(dependsOnMethods = {"loginTestInboxFolder"})
    @Parameters("browser")
    public void sendEmailWithoutProperlyFilledAddressTest(String browser) throws Exception {
        login();
        // send e-mail
        driver.findElement(WRITE_LETTER_LOCATOR).click();
        driver.findElement(ADDRESS_LOCATOR).sendKeys(EMAIL_WITHOUT_PROPERLY_FILLED_ADDRESS);
        driver.findElement(TOPIC_LOCATOR).sendKeys(TOPIC_TEXT_FOR_EMAIL_WITHOUT_PFA);
        fillEmailTextArea(browser);
        driver.findElement(SEND_BUTTON_LOCATOR).click();
        waitSomeTime();
        // We have a prohibiting alert and no e-mail will be issued
        Assert.assertTrue(isAlertPresent());
    }

    @Test(dependsOnMethods = {"sendEmailWithoutProperlyFilledAddressTest"})
    public void sendEmailWithoutSubjectAndBodyTest() {
        login();
        driver.findElement(WRITE_LETTER_LOCATOR).click();
        driver.findElement(ADDRESS_LOCATOR).sendKeys(EMAIL);
        driver.findElement(SEND_BUTTON_LOCATOR).click();
        driver.switchTo().activeElement();
        // We can find warning about empty letter and click the button
        driver.findElement(WARNING_ABOUT_EMPTY_LETTER_LOCATOR).click();
        // check if e-mail exists in the Send folder
        waitSomeTime();
        driver.manage().timeouts().implicitlyWait(ONE_SECOND, TimeUnit.SECONDS);
        driver.findElement(SEND_FOLDER_LOCATOR).click();
        driver.navigate().refresh();
        Assert.assertEquals(
                        driver.findElement(LETTER_LOCATOR).getAttribute(INNER_TEXT).substring(0, NO_SUBJECT.length()),
                        NO_SUBJECT);
    }

    @Test(dependsOnMethods = {"sendEmailWithoutSubjectAndBodyTest"})
    public void emptyMessageIndexFolderTest() {
        login();
        // check if e-mail exists in the Inbox folder
        driver.findElement(INBOX_LOCATOR).click();
        waitSomeTime();
        driver.manage().timeouts().implicitlyWait(ONE_SECOND, TimeUnit.SECONDS);
        Assert.assertEquals(
                        driver.findElement(LETTER_LOCATOR).getAttribute(INNER_TEXT).substring(0, NO_SUBJECT.length()),
                        NO_SUBJECT);
    }

    @Test(dependsOnMethods = {"emptyMessageIndexFolderTest"})
    @Parameters("browser")
    public void createDraftMailTest(String browser) throws Exception {
        login();
        // save draft e-mail
        driver.findElement(WRITE_LETTER_LOCATOR).click();
        waitSomeTime();
        driver.manage().timeouts().implicitlyWait(ONE_SECOND, TimeUnit.SECONDS);
        driver.findElement(ADDRESS_LOCATOR).sendKeys(EMAIL);
        driver.findElement(TOPIC_LOCATOR).sendKeys(TOPIC_TEXT);
        fillEmailTextArea(browser);
        Boolean isPresent = driver.findElements(DRAFT_SAVED_LOCATOR).size() > 0;
        Assert.assertFalse(isPresent);
        driver.findElement(SAVE_DRAFT_LOCATOR).click();
        waitSomeTime();
        isPresent = driver.findElements(DRAFT_SAVED_LOCATOR).size() > 0;
        Assert.assertTrue(isPresent);
    }

    @Test(dependsOnMethods = {"createDraftMailTest"})
    public void deleteDraftMailTest() {
        login();
        // Click draft e-mail
        driver.findElement(DRAFT_LETTER_LOCATOR).click();
        driver.navigate().refresh();
        WebElement webElement = driver.findElement(CBOX_LOCATOR);
        webElement.click();
        // The check if checkbox is clicked
        boolean clicked = webElement.isEnabled();
        Assert.assertTrue(clicked);
        // delete draft e-mail
        new Actions(driver).moveToElement(webElement).sendKeys(Keys.DELETE).build().perform();
        waitSomeTime();
        driver.manage().timeouts().implicitlyWait(ONE_SECOND, TimeUnit.SECONDS);
        // Check that mail appeared in Trash
        driver.findElement(TRASH_FOLDER_LOCATOR).click();
        Boolean isPresent = driver.findElements(CBOX_LOCATOR).size() > 0;
        Assert.assertTrue(isPresent);
        // delete e-mail from Trash folder
        driver.findElement(TRASH_FOLDER_LOCATOR).click();
        driver.navigate().refresh();
        webElement = driver.findElement(CBOX_LOCATOR);
        webElement.click();
        new Actions(driver).moveToElement(webElement).sendKeys(Keys.DELETE).build().perform();
        waitSomeTime();
        driver.manage().timeouts().implicitlyWait(ONE_SECOND, TimeUnit.SECONDS);
        isPresent = driver.findElements(CBOX_LOCATOR).size() > 0;
        Assert.assertFalse(isPresent);
    }

    private void login() {
        driver.get(MAIL_RU);
        WebElement iframe = driver.findElement(IFRAME_LOCATOR);
        driver.switchTo().frame(iframe);
        BaseLoginPage baseLoginPage = PageFactory.initElements(driver, BaseLoginPage.class);
        baseLoginPage.userLogin();
    }

    private boolean isAlertPresent() {
        boolean presentFlag = false;

        try {
            // Check the presence of alert
            Alert alert = driver.switchTo().alert();
            // Alert present; set the flag
            presentFlag = true;
            // if present consume the alert
            alert.accept();
            // ( Now, click on ok or cancel button )
        } catch (NoAlertPresentException ex) {
            // Alert not present
            ex.printStackTrace();
        }

        return presentFlag;
    }

    private void fillEmailTextArea(String browser) {
        if ("firefox".equalsIgnoreCase(browser)) {
            driver.findElement(DELETE_DESIGN_LOCATOR).click();
            driver.findElement(TEXT_AREA_LOCATOR).sendKeys(TEXT_FIELD_TEXT);
        } else if ("chrome".equalsIgnoreCase(browser)) {
            driver.findElement(TEXT_FIELD_LOCATOR).sendKeys(TEXT_FIELD_TEXT);
        }
    }

    @AfterMethod
    protected void killDriver() {
        driver.quit();
    }

    protected void waitSomeTime() {
        try {
            Thread.sleep(MY_WAIT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
