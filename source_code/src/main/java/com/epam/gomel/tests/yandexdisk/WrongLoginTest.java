package com.epam.gomel.tests.yandexdisk;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.epam.gomel.framework.browser.Browser;
import com.epam.gomel.lib.common.Account;
import com.epam.gomel.lib.common.AccountBuilder;
import com.epam.gomel.lib.drive.service.LoginService;

public class WrongLoginTest {

    Account account = AccountBuilder.getAccountWithWrongPass();

    @Test
    public void wrongLoginTest() {
        LoginService.login(account);
        assertTrue(LoginService.isErrorMessageDisplayed());
    }

    @AfterClass
    public void exit() {
        Browser.kill();
    }
}
