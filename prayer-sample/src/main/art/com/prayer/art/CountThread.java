package com.prayer.art;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 
 * @author Lang
 *
 */
public class CountThread extends Thread {

    private transient Method method;

    private transient Object[] args;

    public CountThread(final Method method, final Object[] args) {
        this.method = method;
        this.args = args;
    }

    @Override
    public void run() {
        try {
            Counter.count(this.method, this.args);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
