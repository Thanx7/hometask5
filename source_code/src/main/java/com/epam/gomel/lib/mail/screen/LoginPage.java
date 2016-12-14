package com.epam.gomel.lib.mail.screen;

import org.openqa.selenium.By;

import com.epam.gomel.lib.common.Constants;

public class LoginPage extends Page {
    public static final By INPUT_LOGIN_LOCATOR = By.xpath("//input[@name='Username']");
    public static final By INPUT_PASSWORD_LOCATOR = By.xpath("//input[@name='Password']");
    public static final By BUTTON_SUBMIT_LOCATOR = By.xpath("(//button[@data-name='submit'])[1]");
    public static final By ERROR_LOGIN_MESSAGE_LOCATOR = By.xpath("//div[@class='b-login__errors']");

    public LoginPage open() {
        browser.open(Constants.MAIL_RU_START_PAGE);
        return this;
    }

    public LoginPage fillUserNameInput(String name) {
        browser.type(INPUT_LOGIN_LOCATOR, name, true);
        return new LoginPage();
    }

    public LoginPage fillPasswordInput(String pass) {
        browser.type(INPUT_PASSWORD_LOCATOR, pass, false);
        return new LoginPage();
    }

    public MainPage pressLoginButton() {
        browser.click(BUTTON_SUBMIT_LOCATOR);
        return new MainPage();
    }

    public boolean isErrorDisplayed() {
        return browser.isDisplayed(ERROR_LOGIN_MESSAGE_LOCATOR);
    }

}
