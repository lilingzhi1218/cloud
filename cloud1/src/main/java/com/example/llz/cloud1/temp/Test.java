package com.example.llz.cloud1.temp;


import com.example.llz.commons.utils.UUIDHelper;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Test<E> extends ExtendTest<E> implements InterfaceTest1, InterfaceTest<E>{
    @Override
    void abs() {
        
    }
    @Override
    public void function() {
    }

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Test.class);
        Map<String, Object> map = new HashMap<>();
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            list.add(i);
        }
        list.parallelStream().forEach(e->{
            map.put(UUIDHelper.getString(), e );
        });

    }
}
