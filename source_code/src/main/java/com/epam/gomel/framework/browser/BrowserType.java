package com.epam.gomel.framework.browser;

public enum BrowserType {

    FIREFOX("firefox"), CHROME("chrome");

    String name;

    BrowserType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
