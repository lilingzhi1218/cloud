package com.example.llz.cloud1.Collection;

import com.alibaba.fastjson.JSON;
import com.example.llz.commons.utils.json.JsonUtil;

import java.util.HashMap;

public class MapTest {
    private void compute(){
        HashMap<String, Object> hashMap = new HashMap<>();
        //执行后面表达式存入map中
        hashMap.compute("key", (k, v) -> {
            if (v == null) {
                return "hello";
            } else {
                return v + "hello";
            }
        });
        System.out.println(JsonUtil.toJsonString(hashMap));

        // 如果key存在，则执行表达式存入map中
        hashMap.computeIfPresent("key", (k, v) -> v + " world");
        System.out.println(JsonUtil.toJsonString(hashMap));

        // 如果key不存在，则执行表达式存入map中
        hashMap.putIfAbsent("keyNotExist", "default hello world");
        System.out.println(JsonUtil.toJsonString(hashMap));
    }

    public static void main(String[] args) {
        MapTest mapTest = new MapTest();
        mapTest.compute();
    }
}
