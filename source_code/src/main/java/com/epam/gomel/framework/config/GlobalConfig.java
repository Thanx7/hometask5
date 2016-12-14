package com.epam.gomel.framework.config;

import java.util.List;

import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StringArrayOptionHandler;

import com.epam.gomel.framework.browser.BrowserType;

public class GlobalConfig {

    private static GlobalConfig instance;

    @Option(name = "-bt", usage = "browser type: firefox or chrome")
    private BrowserType browserType = BrowserType.CHROME;

    @Option(name = "-suites", usage = "list of pathes to suites", handler = StringArrayOptionHandler.class,
                    required = true)
    private List<String> suites;

    @Option(name = "-hub", usage = "selenium hub")
    private String seleniumHub = "";

    @Option(name = "-result_dir", usage = "Directory to put results")
    private String resultDir = "results";

    public static GlobalConfig getInstance() {
        if (instance == null) {
            instance = new GlobalConfig();
        }
        return instance;
    }

    public void setBrowserType(BrowserType browserType) {
        this.browserType = browserType;
    }

    public List<String> getSuites() {
        return suites;
    }

    public void setSuites(List<String> suites) {
        this.suites = suites;
    }

    public BrowserType getBrowserType() {
        return browserType;
    }

    public String getSeleniumHub() {
        return seleniumHub;
    }

    public void setSeleniumHub(String seleniumHub) {
        this.seleniumHub = seleniumHub;
    }

    public String getResultDir() {
        return resultDir;
    }

    public void setResultDir(String resultDir) {
        this.resultDir = resultDir;
    }

    @Override
    public String toString() {
        return "GlobalConfig{" + "browserType=" + browserType + ", suites=" + suites + ", seleniumHub='" + seleniumHub
                        + '\'' + ", resultDir='" + resultDir + '\'' + '}';
    }

}
