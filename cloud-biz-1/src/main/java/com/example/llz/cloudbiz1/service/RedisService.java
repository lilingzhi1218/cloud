package com.example.llz.cloudbiz1.service;

import com.example.llz.cloudbiz1.entity.Person;
import com.example.llz.cloudbiz1.iService.IRedisService;
import com.example.llz.cloudbiz1.utils.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;


@Service("redisService")
public class RedisService implements IRedisService {

    private static int ExpireTime = 60;   // redis中存储的过期时间60s

    @Resource
    private RedisUtil redisUtil;

    @Override
    public boolean redisSet(String key, String value) {
        Person person =new Person();
        person.setId(UUID.randomUUID().toString());
        person.setName("llz");
        person.setAge(String.valueOf(20));
        person.setSex("male");
        return redisUtil.set(key, person);
    }

    @Override
    public Object redisGet(String key) {
        return redisUtil.get(key);
    }

    @Override
    public boolean expire(String key) {
        return redisUtil.expire(key, ExpireTime);
    }
}
