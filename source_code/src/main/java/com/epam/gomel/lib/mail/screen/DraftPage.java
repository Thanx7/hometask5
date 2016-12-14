package com.epam.gomel.lib.mail.screen;

import org.openqa.selenium.By;

public class DraftPage extends MainPage {
    private static final String DELETE_MESSAGE_BUTTON_LOCATOR = "(//div[@data-name='remove']"
                    + "//span[@class='b-toolbar__btn__text b-toolbar__btn__text_pad'])[2]";

    public void clickDeleteButton() {
        browser.click(By.xpath(DELETE_MESSAGE_BUTTON_LOCATOR));
    }

    public By checkBoxLocator(String subj) {
        return By.xpath("//div[contains(text(),'" + subj + "')]/parent::*/parent::*/parent::*/parent::*"
                        + "//div[@class='js-item-checkbox b-datalist__item__cbx']");
    }

    public DraftPage markLetter(String subj) {
        browser.click(checkBoxLocator(subj));
        return new DraftPage();
    }
}
