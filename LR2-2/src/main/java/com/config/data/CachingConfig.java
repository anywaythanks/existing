package com.config.data;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;

@Configuration
@EnableCaching
public class CachingConfig {
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }
}
