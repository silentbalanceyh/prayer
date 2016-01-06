package com.prayer.art;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Counter {

    public static void count(Method method, Object... args)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        method.setAccessible(true);
        long start = System.currentTimeMillis();
        for (long idx = 0; idx < 10_0000_0000; idx++) {
            method.invoke(null, args);
        }
        long end = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + ", Time: " + method.getName() + " -> " + (end - start) + " ms");
    }
}
