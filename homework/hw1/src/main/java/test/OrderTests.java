package test;

import annotations.Order;
import annotations.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTests extends BaseTest{
    @Test
    @Order(order = 3)
    public void testOrder3() {
        System.out.println("I am testOrder3");
        assertTrue(true);
    }

    @Test
    @Order(order = 10)
    public void testOrder10() {
        System.out.println("I am testOrder10");
        assertTrue(true);
    }

    @Test
    @Order
    public void testOrderDefault() {
        System.out.println("I am testOrderDefault (5)");
        assertTrue(true);
    }

    @Test
    public void testNoOrder() {
        System.out.println("I am testNoOrder");
        assertTrue(true);
    }
}
