package com.epam.gomel.tests.mail;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.epam.gomel.framework.browser.Browser;
import com.epam.gomel.lib.common.Account;
import com.epam.gomel.lib.common.AccountBuilder;
import com.epam.gomel.lib.mail.service.LoginService;

public class CorrectLoginTest {

    Account account = AccountBuilder.getDefaultAccount();

    @Test
    public void correctLoginTest() {
        LoginService.login(account);
        assertTrue(LoginService.isSuccessfulLogin(account));
    }

    @AfterClass
    public void exit() {
        Browser.kill();
    }
}
