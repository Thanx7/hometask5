package com.epam.gomel.lib.drive.screen;

import org.openqa.selenium.By;

import com.epam.gomel.framework.browser.Browser;

public class DetailsPage extends FilesPage {

    public static final By DOWNLOAD_BUTTON_LOCATOR = By.xpath("//*[@data-click-action='resource.download']");
    public static final By DELETE_BUTTON_LOCATOR = By.xpath("//button[@data-click-action='resource.delete']");
    public static final By RESTORE_BUTTON_LOCATOR = By.xpath("//button[@data-click-action='resource.restore']");

    public void pressRemoveButton() {
        Browser.current().click(DELETE_BUTTON_LOCATOR);
    }

    public void pressRestoreButton() {
        Browser.current().click(RESTORE_BUTTON_LOCATOR);
    }

    public void pressDownloadButton() {
        Browser.current().click(DOWNLOAD_BUTTON_LOCATOR);
    }
}
