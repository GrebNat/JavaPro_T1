import core.TestRunner;
import test.Tests;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        System.out.println(TestRunner.runTests(Tests.class));
    }
}