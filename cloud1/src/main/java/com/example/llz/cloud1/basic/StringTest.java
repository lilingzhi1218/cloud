package com.example.llz.cloud1.basic;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.*;

public class StringTest {
    private void demo(){
        String s = new String(new byte[9]);
    }
    public static void main(String[] args) {
        Integer a = 0;
        List b = new ArrayList<>();
        List<Object> objects = Collections.synchronizedList(new ArrayList<>());
        Map<String, Object> map =new HashMap<>();
        map.put(null, null);
        Map<String, Object> table = new Hashtable<>();
        table.put(null, null);
        ArrayList<Object> objects1 = Lists.newArrayList();
    }
}
