package com.epam.gomel.tests.mail;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.epam.gomel.framework.browser.Browser;
import com.epam.gomel.lib.common.Account;
import com.epam.gomel.lib.common.AccountBuilder;
import com.epam.gomel.lib.mail.Letter;
import com.epam.gomel.lib.mail.LetterBuilder;
import com.epam.gomel.lib.mail.service.LoginService;
import com.epam.gomel.lib.mail.service.MailService;

public class SendFullLetterTest {

    Account account = AccountBuilder.getDefaultAccount();
    Letter letter = LetterBuilder.getFullLetter();

    @BeforeClass
    public void login() {
        LoginService.login(account);
    }

    @Test
    public void sendFullLetterTest() {
        MailService.sendFullLetter(letter);
        assertTrue(MailService.isSuccessfulSendLetter() && MailService.isLetterPresentInSent(letter));
    }

    @AfterClass
    public void exit() {
        Browser.kill();
    }
}
