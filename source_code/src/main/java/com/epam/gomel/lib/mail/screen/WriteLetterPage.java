package com.epam.gomel.lib.mail.screen;

import org.openqa.selenium.By;

public class WriteLetterPage extends MainPage {
    private static final By RECIPIENT_LOCATOR = By.xpath("//textarea[@data-original-name='To']");
    private static final By SUBJECT_LOCATOR = By.xpath("//input[@class='b-input']");
    private static final By BODY_MAIL_LOCATOR = By.xpath("//span[@class='mceEditor defaultSkin']//iframe");
    private static final By SEND_MAIL_BUTTON_LOCATOR =
                    By.xpath("(//div[@data-name='send']//span[@class='b-toolbar__btn__text'])[1]");
    private static final By SAVED_LOCATOR = By.xpath("(//a[@class='toolbar__message_info__link'])[1]");
    private static final By SAVE_DRAFT_BUTTON_LOCATOR = By.xpath("(//div[@data-name='saveDraft']//span)[1]");
    private static final By ATTACH_FILE_LOCATOR =
                    By.xpath("//div[@class='js-input-file compose-attachments__item']//span");
    private static final String PATH_TO_ATTACHMENT = "src/main/resources/";

    public WriteLetterPage fillRecipientInput(String recipient) {
        browser.type(RECIPIENT_LOCATOR, recipient);
        return new WriteLetterPage();
    }

    public WriteLetterPage fillSubjectInput(String subject) {
        browser.type(SUBJECT_LOCATOR, subject);
        return new WriteLetterPage();
    }

    public WriteLetterPage fillBodyInput(String body) {
        browser.type(BODY_MAIL_LOCATOR, body);
        return new WriteLetterPage();
    }

    public WriteLetterPage fillAttachmentInput(String attachment) {
        browser.type(ATTACH_FILE_LOCATOR, PATH_TO_ATTACHMENT, attachment);
        return new WriteLetterPage();
    }

    public SuccessfulSendLetterPage pressSubmitButton() {
        browser.click(SEND_MAIL_BUTTON_LOCATOR);
        return new SuccessfulSendLetterPage();
    }

    public WriteLetterPage pressSaveButton() {
        browser.click(SAVE_DRAFT_BUTTON_LOCATOR);
        return new WriteLetterPage();
    }

    public boolean isSavedAsDraftLetter() {
        return browser.isDisplayed(SAVED_LOCATOR);
    }

}
