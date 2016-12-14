package com.epam.gomel.lib.drive.service;

import com.epam.gomel.lib.common.Account;
import com.epam.gomel.lib.drive.screen.LoginPage;
import com.epam.gomel.lib.drive.screen.TopMenuPage;

public class LoginService {

    public static void login(Account account) {
        new LoginPage().open().fillUserNameInput(account.getUserName()).fillPasswordInput(account.getPassword())
                        .pressLoginButton();
    }

    public static boolean isSuccessfulLogin() {
        return new TopMenuPage().isSuccessfulLogin();
    }

    public static boolean isErrorMessageDisplayed() {
        return new LoginPage().isErrorDisplayed();
    }
}
