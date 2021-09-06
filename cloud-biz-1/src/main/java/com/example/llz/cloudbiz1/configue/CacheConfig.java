package com.example.llz.cloudbiz1.configue;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.DiskStoreConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.config.SizeOfPolicyConfiguration;
import net.sf.ehcache.config.SizeOfPolicyConfiguration.MaxDepthExceededBehavior;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig implements CachingConfigurer {

    @Value("${spring.ehcache.diskpath:java.io.tmpdir/ibasecache}")
    private String path;
    private static final String CACHE_DEFAULT_NAME = "ibase2private";
    @Value("${spring.ehcache.cachename:ibase2private}")
    private String cacheName;
    private static String stcCacheName = "";
    public CacheConfig() {
    }

    public static String getCacheName() {
        return stcCacheName;
    }
    @Bean(
            destroyMethod = "shutdown"
    )
    public CacheManager ehCacheManager() {
        stcCacheName = this.cacheName;
        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
        DiskStoreConfiguration diskStoreConfiguration = new DiskStoreConfiguration();
        diskStoreConfiguration.setPath(this.path);
        config.diskStore(diskStoreConfiguration);
        SizeOfPolicyConfiguration sizeOfPolicy = new SizeOfPolicyConfiguration();
        sizeOfPolicy.maxDepth(500).maxDepthExceededBehavior(SizeOfPolicyConfiguration.MaxDepthExceededBehavior.ABORT);
        config.sizeOfPolicy(sizeOfPolicy);
        CacheConfiguration defaultCache = new CacheConfiguration();
        defaultCache.setMaxEntriesLocalHeap(0L);
        defaultCache.setMaxBytesLocalHeap(536870912L);
        defaultCache.setEternal(false);
        defaultCache.setTimeToIdleSeconds(30L);
        defaultCache.setTimeToLiveSeconds(30L);
        defaultCache.setMemoryStoreEvictionPolicy("LFU");
        config.addDefaultCache(defaultCache);
        CacheConfiguration iBase2PrivateCache = new CacheConfiguration(this.cacheName, 2000);
        iBase2PrivateCache.setMaxEntriesLocalHeap(0L);
        iBase2PrivateCache.setMaxBytesLocalHeap(536870912L);
        iBase2PrivateCache.setMaxEntriesLocalDisk(0L);
        iBase2PrivateCache.setEternal(false);
        iBase2PrivateCache.setTimeToIdleSeconds(60L);
        iBase2PrivateCache.setTimeToLiveSeconds(60L);
        iBase2PrivateCache.setMemoryStoreEvictionPolicy("LFU");
        iBase2PrivateCache.setTransactionalMode("off");
        PersistenceConfiguration persistenceConfiguration = new PersistenceConfiguration();
        persistenceConfiguration.setStrategy("localTempSwap");
        iBase2PrivateCache.persistence(persistenceConfiguration);
        config.addCache(iBase2PrivateCache);
        return CacheManager.newInstance(config);
    }

    @Bean
    public org.springframework.cache.CacheManager cacheManager() {
        return new EhCacheCacheManager(this.ehCacheManager());
    }

    @Override
    public CacheResolver cacheResolver() {
        return null;
    }

    @Override
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }


    @Override
    public CacheErrorHandler errorHandler() {
        return null;
    }
}
