package com.epam.gomel.tests.mail;

import static org.testng.AssertJUnit.assertTrue;

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

public class SaveAndDeleteDraftLetterTest {

    Account account = AccountBuilder.getDefaultAccount();
    Letter letter = LetterBuilder.getFullLetter();

    @BeforeClass
    public void login() {
        LoginService.login(account);
    }

    @Test
    public void saveLetterAsDraftTest() {
        assertTrue(MailService.isSavedDraftLetter(letter));
    }

    @Test(dependsOnMethods = "saveLetterAsDraftTest")
    public void deleteLetterFromDraftTest() {
        MailService.deleteDraftLetter(letter);
        assertTrue(MailService.isLetterPresentInTrash(letter));
    }

    @Test(dependsOnMethods = "deleteLetterFromDraftTest")
    public void deleteLetterFromTrashTest() {
        assertTrue(MailService.deleteLetterFromTrash(letter));
    }

    @AfterClass
    public void exit() {
        Browser.kill();
    }
}
