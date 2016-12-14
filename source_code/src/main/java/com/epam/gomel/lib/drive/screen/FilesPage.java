package com.epam.gomel.lib.drive.screen;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import com.epam.gomel.framework.browser.Browser;

public class FilesPage extends TopMenuPage {

    public static final String FILE_LOCATOR = "//div[contains(@data-id, 'disk/%s')]";
    public static final By TRASH_PICTURE_LOCATOR = By.xpath("//div[@data-id='/trash']");

    public boolean isTrueFile(String name) {
        Browser.current().refresh();
        return Browser.current().isDisplayed(By.xpath(String.format(FILE_LOCATOR, name)));
    }

    public DetailsPage markFile(String name) {
        Browser.current().click(By.xpath(String.format(FILE_LOCATOR, name)));
        return new DetailsPage();
    }

    public void removeFile(String name) {
        Browser.current().dragAndDrop(By.xpath(String.format(FILE_LOCATOR, name)), TRASH_PICTURE_LOCATOR);
    }

    public void removeFiles(List<String> filesName) {
        List<By> locators = new ArrayList<By>();
        for (String fileName : filesName) {
            locators.add(By.xpath(String.format(FILE_LOCATOR, fileName)));
        }

        By locator = Browser.current().selectSeveralElements(locators);
        Browser.current().dragAndDrop(locator, TRASH_PICTURE_LOCATOR);
    }
}
