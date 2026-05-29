package core;

import annotations.Order;
import exceptions.BadTestClassError;
import exceptions.TestAssertionError;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static java.util.Comparator.comparingInt;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.groupingBy;

public class TestRunner {
    public static Map<TestResult, List<Test>> runTests(Class<?> clazz) throws InvocationTargetException, IllegalAccessException {
        Object instance;

        try {
            instance = clazz.getDeclaredConstructor().newInstance();
        } catch (Throwable e) {
            throw new BadTestClassError("I cant create test class object");
        }

        Method[] methods = clazz.getDeclaredMethods();
        Method[] allMethods = clazz.getMethods();
        List<Test> result = new ArrayList<>();

        Method[] testMethods = sortByOrder(sortByPriority(getTestMethods(methods)));
        Method beforeEachMethod = getBeforeEachMethod(methods);
        Method afterEachMethod = getAfterEachMethod(methods);
        Method beforeSuiteMethod = getBeforeSuiteMethod(allMethods);
        Method afterSuiteMethod = getAfterSuiteMethod(allMethods);

        if (beforeSuiteMethod != null) beforeSuiteMethod.invoke(instance);

        for (Method testMethod : testMethods) {
            Test test = executeTest(instance, testMethod, beforeEachMethod, afterEachMethod);

            if (test != null)
                result.add(test);
        }

        if (afterSuiteMethod != null) afterSuiteMethod.invoke(instance);

        return result.stream().collect(groupingBy(Test::getTestResult));
    }

    public static Test executeTest(Object instance, Method method, Method beforeEachMethod, Method afterEachMethod)
            throws InvocationTargetException, IllegalAccessException {
        Test test = null;

        if (isTest(method)) {
            if (isStatic(method)) throw new BadTestClassError("@Test method should not be static");
            if (isDisabled(method)) {
                test = new Test(method.getName(), TestResult.SKIPPED);
            } else {
                if (beforeEachMethod != null)
                    beforeEachMethod.invoke(instance);

                try {
                    method.invoke(instance);
                    test = new Test(method.getName(), TestResult.SUCCESS);
                } catch (Throwable e) {
                    if (((InvocationTargetException) e).getTargetException() instanceof TestAssertionError)
                        test = new Test(method.getName(), TestResult.FAILED, ((InvocationTargetException) e).getTargetException().getMessage());
                    else
                        test = new Test(method.getName(), TestResult.ERROR, ((InvocationTargetException) e).getTargetException().getMessage());
                } finally {
                    if (afterEachMethod != null)
                        afterEachMethod.invoke(instance);
                }
            }
        }

        return test;
    }

    public static Method[] getTestMethods(Method[] methods) {
        return stream(methods)
                .filter(method -> method.isAnnotationPresent(annotations.Test.class))
                .toArray(Method[]::new);
    }

    public static Method getBeforeEachMethod(Method[] methods) {
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotations.BeforeEach.class)) {
                if (!isStatic(method))
                    return method;
                else
                    throw new BadTestClassError("@BeforeEach method should not be static");
            }
        }
        return null;
    }

    public static Method getAfterEachMethod(Method[] methods) {
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotations.AfterEach.class)) {
                if (!isStatic(method))
                    return method;
                else
                    throw new BadTestClassError("@AfterSuite method should not be static");
            }
        }
        return null;
    }

    public static Method getBeforeSuiteMethod(Method[] methods) {
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotations.BeforeSuite.class)) {
                if (isStatic(method))
                    return method;
                else
                    throw new BadTestClassError("@BeforeSuite method must be static");
            }
        }
        return null;
    }

    public static Method getAfterSuiteMethod(Method[] methods) {
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotations.AfterSuite.class)) {
                if (isStatic(method))
                    return method;
                else
                    throw new BadTestClassError("@AfterSuite method must be static");
            }
        }
        return null;
    }

    public static Boolean isTest(Method method) {
        return method.isAnnotationPresent(annotations.Test.class);
    }

    public static Integer getOrder(Method method) {
        return method.isAnnotationPresent(Order.class) ? method.getAnnotation(Order.class).order() : 0;
    }

    public static Integer getPriority(Method method) {
        return method.getAnnotation(annotations.Test.class).priority();
    }

    public static Boolean isDisabled(Method method) {
        return method.isAnnotationPresent(annotations.Disabled.class);
    }

    public static Boolean isStatic(Method method) {
        return Modifier.isStatic(method.getModifiers());
    }

    public static Method[] sortByOrder(Method[] methods) {
        return stream(methods)
                .sorted(comparingInt(TestRunner::getOrder))
                .toArray(Method[]::new);
    }

    public static Method[] sortByPriority(Method[] methods) {
        return stream(methods)
                .sorted(comparingInt(TestRunner::getPriority))
                .toArray(Method[]::new);
    }
}
