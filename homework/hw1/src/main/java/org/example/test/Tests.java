package org.example.test;

import org.example.exceptions.TestAssertionError;
import org.example.main.annotations.Disabled;
import org.example.main.annotations.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Tests {

    @Test
    public void testSuccess1() {
        System.out.println("I am success test 1");
        assertTrue(true);
    }

    @Test
    public void testSuccess2() {
        System.out.println("I am success test 2");
        assertTrue(true);
    }

    @Disabled
    @Test
    public void testSkipped() {
        System.out.println("I am skipped test");
        assertTrue(true);
    }

    @Test
    public void testFiled() {
        System.out.println("I am failed test");
        assertTrue(false);
    }

    @Test
    public void testError() throws TestAssertionError {
        System.out.println("I am error test");
        throw new TestAssertionError("I am test 4, but I am throwing an error");
    }
}
