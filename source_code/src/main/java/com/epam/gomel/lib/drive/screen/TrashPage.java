package com.epam.gomel.lib.drive.screen;

import org.openqa.selenium.By;

import com.epam.gomel.framework.browser.Browser;

public class TrashPage extends TopMenuPage {

    public static final String FILE_LOCATOR = "//div[contains(@data-id, 'trash/%s')]";

    public DetailsPage markFile(String name) {
        Browser.current().click(By.xpath(String.format(FILE_LOCATOR, name)));
        return new DetailsPage();
    }

    public boolean isFilePresent(String name) {
        return Browser.current().isDisplayed(By.xpath(String.format(FILE_LOCATOR, name)));
    }

}
