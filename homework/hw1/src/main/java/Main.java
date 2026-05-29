import core.TestRunner;
import test.OrderTests;
import test.PriorityTests;
import test.Tests;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        System.out.println(TestRunner.runTests(PriorityTests.class));
    }
}