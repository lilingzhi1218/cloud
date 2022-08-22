package com.example.llz.cloud1.Collection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Collection的两个子接口List和Set
 * List的特性是元素有序可重复
 * Set的特性是元素无序不可重复
 */

public class CompareListAndSet {
    
    public static void main(String[] args) {
        Integer a = 12;
        Integer b = 12;
        Integer c = a+b;
        List<Integer> arrayList = new ArrayList<>();
        arrayList.add(a);
        arrayList.add(a);
        arrayList.add(c);
        System.out.println(arrayList); //[12, 12, 24]
        Set<Integer> hashSet = new HashSet<>();
        hashSet.add(a);
        hashSet.add(a);
        hashSet.add(c);
        System.out.println(hashSet); //[24, 12]
    }
}
