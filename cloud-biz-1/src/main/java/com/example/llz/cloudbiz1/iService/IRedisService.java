package com.example.llz.cloudbiz1.iService;

import com.example.llz.cloudbiz1.entity.Person;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository("redisService")
public interface IRedisService {
    boolean redisTemplateSet(String key, String value);
    Object redisTemplateGet(String key);
    boolean redisTemplateExpire(String key);

    @Cacheable(value = "person", key = "#key")
    Object annotationGet(String key);

    @CacheEvict(value = "person", key = "#person")
    void annotationSet(Person person);
}
