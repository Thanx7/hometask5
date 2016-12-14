package com.epam.gomel.lib.mail.screen;

import org.openqa.selenium.By;

public class SuccessfulSendLetterPage extends WriteLetterPage {
    public static final By SUCCESS_MESSAGE = By.xpath("//div[@class='message-sent__title']");

    public boolean isSuccessfulSendLetter() {
        return browser.isDisplayed(SUCCESS_MESSAGE);
    }
}
