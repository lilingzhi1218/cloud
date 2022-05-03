package com.example.llz.cloud1.jvm;

import java.lang.ref.WeakReference;

public class WeekReferenceTest {
    public static void main(String[] args) {

        Object o1 = new Object();
        WeakReference<Object> weakReference = new WeakReference<Object>(o1);
        o1 = null;
        System.gc();
        Object o = weakReference.get();
        System.out.println(o);
    }
}
