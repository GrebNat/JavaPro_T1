package org.example.main.core;

import org.example.exceptions.BadTestClassError;
import org.example.exceptions.TestAssertionError;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestRunner {
    public static Map<TestResult, List<Test>> runTests(Class<?> clazz) {
        Object instance;

        try {
            instance = clazz.getDeclaredConstructor().newInstance();
        } catch (Throwable e) {
            throw new BadTestClassError("I cant create test class object");
        }

        Method[] methods = clazz.getDeclaredMethods();
        List<Test> result = new ArrayList<>();

        for (Method method : methods) {
            Test test = null;

            if (isTest(method)) {
                if (isDisabled(method)) test = new Test(method.getName(), TestResult.SKIPPED);
                else {
                    try {
                        method.invoke(instance);
                        test = new Test(method.getName(), TestResult.SUCCESS);
                    } catch (Throwable e) {
                        if (((InvocationTargetException) e).getTargetException() instanceof TestAssertionError)
                            test = new Test(method.getName(), TestResult.FAILED, ((InvocationTargetException) e).getTargetException().getMessage());
                        else
                            test = new Test(method.getName(), TestResult.ERROR, ((InvocationTargetException) e).getTargetException().getMessage());
                    }
                }
            }

            result.add(test);
        }

        return result.stream().collect(Collectors.groupingBy(Test::getTestResult));
    }

    public static Boolean isTest(Method method) {
        return method.isAnnotationPresent(org.example.main.annotations.Test.class);
    }

    public static Boolean isDisabled(Method method) {
        return method.isAnnotationPresent(org.example.main.annotations.Disabled.class);
    }
}
