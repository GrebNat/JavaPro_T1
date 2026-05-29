package test;

import annotations.AfterSuite;
import annotations.BeforeSuite;

public class BaseTest {

    @BeforeSuite
    public static void beforeSuite() {
        System.out.println("I am before suite");
        System.out.println();
    }

    @AfterSuite
    public static void afterSuite() {
        System.out.println();
        System.out.println("I am after suite");
    }
}
