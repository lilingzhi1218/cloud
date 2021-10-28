package com.example.llz.cloudbiz1.iService;

import com.example.llz.cloudbiz1.entity.Person;
import org.springframework.stereotype.Repository;

@Repository("redisService")
public interface IRedisService {
    boolean redisTemplateSet(String key, String value);
    Object redisTemplateGet(String key);
    boolean redisTemplateExpire(String key);

    Object annotationGet(String key);

    void annotationEvict(Person person);
}
