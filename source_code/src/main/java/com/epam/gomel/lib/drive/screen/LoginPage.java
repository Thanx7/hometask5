package com.epam.gomel.lib.drive.screen;

import org.openqa.selenium.By;

import com.epam.gomel.framework.browser.Browser;
import com.epam.gomel.lib.common.Constants;

public class LoginPage {

    public static final By INPUT_LOGIN_LOCATOR = By.xpath("//input[@name='login']");
    public static final By INPUT_PASSWORD_LOCATOR = By.xpath("//input[@name='password']");
    public static final By BUTTON_SUBMIT_LOCATOR = By.xpath("//button[@type='submit']");

    public static final By ERROR_LOGIN_MESSAGE_LOCATOR = By.cssSelector(".popup-auth__content");

    public LoginPage open() {
        Browser.current().open(Constants.YANDEX_DISK);
        return this;
    }

    public LoginPage fillUserNameInput(String name) {
        Browser.current().type(INPUT_LOGIN_LOCATOR, name);
        return new LoginPage();
    }

    public LoginPage fillPasswordInput(String pass) {
        Browser.current().type(INPUT_PASSWORD_LOCATOR, pass);
        return new LoginPage();
    }

    public TopMenuPage pressLoginButton() {
        Browser.current().click(BUTTON_SUBMIT_LOCATOR);
        return new TopMenuPage();
    }

    public boolean isErrorDisplayed() {
        return Browser.current().isDisplayed(ERROR_LOGIN_MESSAGE_LOCATOR);
    }

}
