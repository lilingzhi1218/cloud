package com.example.llz.cloud1.redis;

import com.example.llz.cloud1.entity.Person;
import com.example.llz.cloud1.redis.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * redis测试控制类
 */
@Controller
@RequestMapping("redis")
public class RedisController {

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
    @RequestMapping("redisTemplateSet")
    @ResponseBody
    public boolean redisTemplateSet(String key, String value){
        return this.redisService.redisTemplateSet(key, value);
    }

    @RequestMapping("redisTemplateGet")
    @ResponseBody
    public Object redisTemplateGet(String key){
        return this.redisService.redisTemplateGet(key);

    }
    @RequestMapping("redisTemplateExpire")
    public boolean expire(String key){
        return this.redisService.redisTemplateExpire(key);
    }

    @RequestMapping("annotationGet")
    @ResponseBody
    public Object get(String key){
        return this.redisService.annotationGet(key);
    }

    @RequestMapping("annotationEvict")
    @ResponseBody
    public void set(@RequestBody Person person){
        this.redisService.annotationEvict(person);
    }

}
