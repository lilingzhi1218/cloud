package com.example.llz.cloudbiz1.iService;

import org.springframework.stereotype.Repository;

@Repository("redisService")
public interface IRedisService {
    boolean redisSet(String key, String value);
    Object redisGet(String key);
    boolean expire(String key);
}
