package com.epam.gomel.lib.mail.screen;

import org.openqa.selenium.By;

public class TrashPage extends MainPage {
    private static final String DELETE_MESSAGE_BUTTON_LOCATOR = "(//div[@data-name='remove'])[1]";

    public void clickDeleteButton() {
        browser.click(By.xpath(DELETE_MESSAGE_BUTTON_LOCATOR));
    }

    public TrashPage markLetter(String subj) {
        browser.click(checkBoxLocator(subj));
        return new TrashPage();
    }

    public By checkBoxLocator(String subj) {
        return By.xpath("//div[contains(text(),'" + subj + "')]/parent::*/parent::*/parent::*/parent::*"
                        + "//div[@class='js-item-checkbox b-datalist__item__cbx']");
    }

    public By letterLocator(String subj) {
        return By.xpath("//div[contains(text(),'" + subj + "')]");
    }

    public boolean isPresentLetter(String subj) {
        return browser.isDisplayed(letterLocator(subj));
    }

}
