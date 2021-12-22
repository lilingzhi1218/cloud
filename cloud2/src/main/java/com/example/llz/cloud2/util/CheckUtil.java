package com.example.llz.cloud2.util;

public class CheckUtil {
    public static long checkTime(long start, String title) {
        long next = System.currentTimeMillis();
        StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        if (start > 0L) {
            System.err.printf("[%s](%03d)%s用时：%d ms\n", ste[2].getMethodName(), ste.length, title, next - start);
        } else if (title != null) {
            System.err.printf("[%s](%03d)%s开始计时================================\n", ste[2].getMethodName(), ste.length, title);
        }

        return next;
    }
}
