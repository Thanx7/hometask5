package com.epam.gomel.lib.mail.service;

import com.epam.gomel.lib.common.Account;
import com.epam.gomel.lib.mail.screen.LoginPage;
import com.epam.gomel.lib.mail.screen.MainPage;

public class LoginService {

    public static void login(Account account) {
        new LoginPage().open().fillUserNameInput(account.getUserName()).fillPasswordInput(account.getPassword())
                        .pressLoginButton();
    }

    public static boolean isSuccessfulLogin(Account account) {
        return new MainPage().isTrueUserLogin().equals(account.getEmail());
    }

    public static boolean isErrorMessageDisplayed() {
        return new LoginPage().isErrorDisplayed();
    }
}
