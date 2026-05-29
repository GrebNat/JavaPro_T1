package test;

import annotations.AfterEach;
import annotations.BeforeEach;
import annotations.Disabled;
import annotations.Test;
import exceptions.TestAssertionError;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PriorityTests extends BaseTest{
    @Test(priority = 3)
    public void testPriority3() {
        System.out.println("I am testPriority3");
        assertTrue(true);
    }

    @Test(priority = 10)
    public void testPriority10() {
        System.out.println("I am testPriority10");
        assertTrue(true);
    }

    @Test
    public void testPriorityDefault() {
        System.out.println("I am testPriorityDefault (5)");
        assertTrue(true);
    }
}
