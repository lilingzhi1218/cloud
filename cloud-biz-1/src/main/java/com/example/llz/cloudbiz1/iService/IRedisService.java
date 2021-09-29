package com.example.llz.cloudbiz1.iService;

import com.example.llz.cloudbiz1.entity.Person;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository("redisService")
public interface IRedisService {
    boolean redisSet(String key, String value);
    Object redisGet(String key);
    boolean expire(String key);

    @Cacheable(value = "person", key = "#key")
    Object get(String key);

    @CacheEvict(value = "person", key = "#person")
    void set(Person person);
}
