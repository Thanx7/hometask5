package com.epam.gomel.lib.mail.screen;

import org.openqa.selenium.By;

public class InboxPage extends MainPage {

    public static final By WRITE_MESSAGE_BUTTON_LOCATOR = By.xpath("(//a[@class='b-toolbar__btn js-shortcut']//"
                    + "span[@class='b-toolbar__btn__text b-toolbar__btn__text_pad'])[1]");
    public static final By DELETE_MESSAGE_BUTTON_LOCATOR = By.xpath("(//div[@data-name='remove'])[1]");

    public WriteLetterPage openWriteMailPage() {
        browser.click(WRITE_MESSAGE_BUTTON_LOCATOR);
        return new WriteLetterPage();
    }

    public void deleteMail() {
        browser.click(DELETE_MESSAGE_BUTTON_LOCATOR);
    }
}
