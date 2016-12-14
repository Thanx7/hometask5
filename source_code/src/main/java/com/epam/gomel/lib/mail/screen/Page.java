package com.epam.gomel.lib.mail.screen;

import com.epam.gomel.framework.browser.Browser;

public class Page {
    protected static Browser browser;

    public Page() {
        browser = Browser.current();
    }
}
