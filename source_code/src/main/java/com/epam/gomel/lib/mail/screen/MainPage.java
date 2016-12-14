package com.epam.gomel.lib.mail.screen;

import org.openqa.selenium.By;

public class MainPage extends Page {
    public static final By HEADER_USER_NAME_LOCATOR = By.xpath("//*[@id='PH_user-email']");

    public static final By INBOX_LOCATOR = By.xpath("(//a[@href='/messages/inbox/'])[2]");
    public static final By SENT_LOCATOR = By.xpath("//a[@href='/messages/sent/']//span");
    public static final By DRAFT_LOCATOR = By.xpath("//a[@href='/messages/drafts/']//span");
    public static final By TRASH_LOCATOR = By.xpath("//a[@href='/messages/trash/']//div");

    public String isTrueUserLogin() {
        return browser.read(HEADER_USER_NAME_LOCATOR);
    }

    public InboxPage openInboxPage() {
        browser.click(INBOX_LOCATOR);
        return new InboxPage();
    }

    public SentPage openSentPage() {
        browser.click(SENT_LOCATOR);
        return new SentPage();
    }

    public DraftPage openDraftPage() {
        browser.click(DRAFT_LOCATOR);
        return new DraftPage();
    }

    public TrashPage openTrashPage() {
        browser.click(TRASH_LOCATOR);
        return new TrashPage();
    }
}
