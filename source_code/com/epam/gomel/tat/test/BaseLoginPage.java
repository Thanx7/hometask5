package com.epam.gomel.tat.test;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class BaseLoginPage {
    public static final String LOGIN = "shura.dubeykovskiy";

    @FindBy(how = How.XPATH, xpath = "//input[@name='Username']")
    private WebElement userName;
    @FindBy(how = How.XPATH, xpath = "//input[@name='Password']")
    private WebElement password;
    @FindBy(how = How.XPATH, xpath = "(//button[@data-name='submit'])[1]")
    private WebElement loginBtn;

    @FindBy(how = How.XPATH, xpath = "//input[@id='nb-22']")
    private WebElement userYandexDisk;
    @FindBy(how = How.XPATH, xpath = "//input[@id='nb-23']")
    private WebElement passwordYandexDisk;
    @FindBy(how = How.XPATH, xpath = "//button[@id='nb-21']")
    private WebElement loginBtnYandexDisk;

    public void userLogin() {
        userName.sendKeys(Tests.EMAIL);
        password.sendKeys(Tests.CORRECT_PASS);
        loginBtn.click();
    }

    public void userYandexDiskLogin() {
        userYandexDisk.sendKeys(LOGIN);
        passwordYandexDisk.sendKeys(Tests.CORRECT_PASS);
        loginBtnYandexDisk.click();
    }
}
