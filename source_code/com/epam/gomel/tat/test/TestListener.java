package com.epam.gomel.tat.test;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

public class TestListener implements IInvokedMethodListener {

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        System.out.println("[METHOD_STARTED] - " + method.getTestMethod().getMethodName());
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (testResult.getStatus() == 1) {
            System.out.println(String.format("[METHOD_FINISHED] - %s >>> %s", method.getTestMethod().getMethodName(),
                            "Success"));
        } else {
            System.out.println(String.format("[METHOD_FINISHED] - %s >>> %s", method.getTestMethod().getMethodName(),
                            "Fail"));
        }
    }
}
