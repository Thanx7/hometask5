package com.epam.gomel.lib.mail.screen;

import org.openqa.selenium.By;

import com.epam.gomel.lib.mail.Letter;

public class SentPage extends MainPage {

    public boolean isLetterPresent(Letter letter) {
        String letterPattern = "//div[contains(text(),'" + letter.getSubject() + "')]";
        return browser.isDisplayed(By.xpath(letterPattern));
    }

    public boolean isLetterPresentWithoutSubject(Letter letter) {
        String letterPattern = "//div[contains(text(),'<')]";
        return browser.isDisplayed(By.xpath(letterPattern));
    }
}
