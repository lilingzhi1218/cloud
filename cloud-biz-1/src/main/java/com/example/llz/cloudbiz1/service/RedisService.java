package com.example.llz.cloudbiz1.service;

import com.example.llz.cloudbiz1.utils.CheckUtil;
import net.sf.ehcache.CacheManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

@Service("redisService")
public class RedisService {

    @Autowired(required = false)
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private CacheManager cacheManager;

    public RedisService() {
    }

    private Boolean checkItemKey(String sKey) {
        if (CheckUtil.isNullorEmpty(sKey)) {
            return false;
        } else {
            int iInx = sKey.lastIndexOf("_");
            String sGuid = sKey;
            if (iInx >= 0) {
                sGuid = sKey.substring(iInx + 1);
            }

            return !CheckUtil.isNullorEmpty(sGuid) && sGuid.length() >= 1;
        }
    }


    public Set<String> findRedisKeys(String key) {
        return this.redisTemplate.keys(key);
    }
}
