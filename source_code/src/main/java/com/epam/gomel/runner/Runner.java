package com.epam.gomel.runner;

import java.util.Arrays;
import java.util.List;

import org.testng.TestNG;

public class Runner {

    public static void main(String[] args) {
        TestNG testNG = new TestNG();
        testNG.addListener(new TestListener());
        List<String> files = Arrays.asList("./src/main/resources/suites/tests.xml");
        testNG.setTestSuites(files);
        testNG.run();
    }
}
