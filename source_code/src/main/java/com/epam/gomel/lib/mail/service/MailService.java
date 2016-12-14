package com.epam.gomel.lib.mail.service;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;

import com.epam.gomel.framework.browser.Browser;
import com.epam.gomel.lib.mail.Letter;
import com.epam.gomel.lib.mail.screen.MainPage;
import com.epam.gomel.lib.mail.screen.SuccessfulSendLetterPage;

public class MailService {
    private static final By WARNING_ABOUT_EMPTY_LETTER_LOCATOR = By.xpath("//div[@class='is-compose-empty_in']"
                    + "//button[@class='btn btn_stylish btn_main confirm-ok']//span");

    public static void sendFullLetter(Letter letter) {
        new MainPage().openInboxPage().openWriteMailPage().fillRecipientInput(letter.getRecipient())
                        .fillSubjectInput(letter.getSubject()).fillBodyInput(letter.getBody())
                        .fillAttachmentInput(letter.getAttachment()).pressSubmitButton();
    }

    public static void sendLetterWithoutRecipient(Letter letter) {
        new MainPage().openInboxPage().openWriteMailPage().fillSubjectInput(letter.getSubject())
                        .fillBodyInput(letter.getBody()).pressSubmitButton();
    }

    public static void sendLetterWithoutSubjectAndBody(Letter letter) {
        new MainPage().openInboxPage().openWriteMailPage().fillRecipientInput(letter.getRecipient())
                        .pressSubmitButton();
    }

    public static boolean isSavedDraftLetter(Letter letter) {
        return new MainPage().openInboxPage().openWriteMailPage().fillRecipientInput(letter.getRecipient())
                        .fillSubjectInput(letter.getSubject()).fillBodyInput(letter.getBody()).pressSaveButton()
                        .isSavedAsDraftLetter();
    }

    public static void deleteDraftLetter(Letter letter) {
        new MainPage().openDraftPage().markLetter(letter.getSubject()).clickDeleteButton();
    }

    public static boolean deleteLetterFromTrash(Letter letter) {
        Browser.current().refresh();
        new MainPage().openTrashPage().markLetter(letter.getSubject()).clickDeleteButton();
        return true;
    }

    public static boolean isLetterPresentInTrash(Letter letter) {
        Browser.current().refresh();
        return new MainPage().openTrashPage().isPresentLetter(letter.getSubject());
    }

    public static boolean isSuccessfulSendLetter() {
        return new SuccessfulSendLetterPage().isSuccessfulSendLetter();
    }

    public static boolean isLetterPresentInSent(Letter letter) {
        Browser.current().refresh();
        return new MainPage().openSentPage().isLetterPresent(letter);
    }

    public static boolean isLetterWithoutSubjectPresentInSent(Letter letter) {
        return new MainPage().openSentPage().isLetterPresentWithoutSubject(letter);
    }

    public static boolean isAlertPresent() {
        boolean presentFlag = false;

        try {
            // Check the presence of alert
            Alert alert = Browser.current().getWrappedDriver().switchTo().alert();
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

    public static void clickTheWarning() {
        Browser.current().getWrappedDriver().findElement(WARNING_ABOUT_EMPTY_LETTER_LOCATOR).click();
    }
}
