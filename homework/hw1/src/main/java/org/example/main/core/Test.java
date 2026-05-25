package org.example.main.core;

public class Test {
    public String testName;
    public TestResult testResult;
    public String failMessage = null;

    public Test(String name, TestResult testResult) {
        testName = name;
        this.testResult = testResult;
    }

    public Test(String name, TestResult testResult, String message) {
        testName = name;
        this.testResult = testResult;
        failMessage = message;
    }

    public TestResult getTestResult() {
        return testResult;
    }

    @Override
    public String toString() {
        return testName;
    }
}
