package com.prayer.art.p3;

import java.lang.reflect.Method;

import com.prayer.art.CountThread;

/**
 * [Alogrithem E] Euclid's Algorithm: Greatest common divisor.
 * 
 * @author Lang
 *
 */
// E0.[Ensurm m > n.] If m < n, exchange m <-> n
// E1.[Find remainder.] Divide m by n and let r be the remainder. ( 0 <= r < n )
// E2.[Is it zero?] If r = 0, the algorithm terminates; n is the answer.
// E3.[Reduce.] Set m <- n, n <- r, and go back to step E1.
public class Euclid {
    
    @SuppressWarnings("unused")
    private static void performance(Object[] args) throws Exception {
        // Performance Checking
        final Method methodE = Euclid.class.getDeclaredMethod("gcdE", new Class[] { int.class, int.class });
        final Method methodF = Euclid.class.getDeclaredMethod("gcdF", new Class[] { int.class, int.class });
        final Method methodE1 = Euclid.class.getDeclaredMethod("gcdE1", new Class[] { int.class, int.class });
        final CountThread tE = new CountThread(methodE, args);
        final CountThread tF = new CountThread(methodF, args);
        final CountThread tE1 = new CountThread(methodE1, args);
        tE.start();
        tF.start();
        tE1.start();
    }

    // args[0] = 1769
    // args[1] = 551
    // result = 29
    public static void main(String args[]) throws Exception {
        /**
         * 参数必须是两个数字，而且args[0] > args[1]，因为是算法分析，所以不考虑非法输入
         */
        int m = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        // Alogrithem E
        System.out.println(Thread.currentThread().getName() + ", Result = " + gcdE(m, n));
        // Alogrithem F
        System.out.println(Thread.currentThread().getName() + ", Result = " + gcdF(m, n));
        // Alogrithem E1
        System.out.println(Thread.currentThread().getName() + ", Result = " + gcdE1(m, n));
        // Performance
        
        // performance(new Object[] { m, n });
        // Thread-0, Time: gcdE  -> 52890 ms
        // Thread-1, Time: gcdF  -> 53242 ms
        // Thread-2, Time: gcdE1 -> 53965 ms

    }

    /**
     * P9 -> 8.[M25]
     * 
     * @param m
     * @param n
     * @return
     */
    // E1. r <- | m - n |, n <- min(m,n).
    private static int gcdE1(int m, int n) {
        int r = Math.abs(m - n);
        // Debug Workflow
        // System.out.println("m : " + m + ", n : " + n + ", r : " + r);
        if (0 == r) {
            return n;
        } else {
            return gcdE1(Math.min(m, n), r);
        }
    }

    /**
     * P9 -> 3.[20]
     * 
     * @param m
     * @param n
     * @return
     */
    private static int gcdF(int m, int n) {
        int r = m % n;
        if (0 == r) {
            return n;
        } else {
            // Avoid Trivial Replacement
            return gcdF(n, r);
        }
    }

    /**
     * 最大公约数，递归算法
     * 
     * @param m
     * @param n
     * @return
     */
    private static int gcdE(int m, int n) {
        /**
         * 查找m / n的余数
         */
        int r = m % n;
        if (0 == r) {
            return n;
        } else {
            m = n;
            n = r;
            return gcdE(m, n);
        }
    }
}
