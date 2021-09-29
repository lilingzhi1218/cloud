package com.example.llz.cloudbiz1.controller;

import com.example.llz.cloudbiz1.entity.Person;
import com.example.llz.cloudbiz1.iService.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("person")
public class PersonController {

    @Autowired
    IRedisService redisService;
    
    @RequestMapping("getPerson")
    @ResponseBody
    public Object getPerson(){
        Map<String, Object> person = new HashMap<>();
        person.put("姓名：", "张三");
        person.put("性别：", "男");
        return person;
    }
    @RequestMapping("redisSet")
    @ResponseBody
    public boolean redisSet(String key, String value){
        return this.redisService.redisSet(key, value);
    }

    @RequestMapping("redisGet")
    @ResponseBody
    public Object redisGet(String key){
        return this.redisService.redisGet(key);

    }
    @RequestMapping("expire")
    public boolean expire(String key){
        return this.redisService.expire(key);
    }

    @RequestMapping("get")
    @ResponseBody
    public Object get(String key){
        return this.redisService.get(key);
    }

    @RequestMapping("set")
    @ResponseBody
    public void set(@RequestBody Person person){
        this.redisService.set(person);
    }

}
