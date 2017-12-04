package com.xiaoyue.nov.practice.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by xiaoyue26 on 17/12/4.
 */
@Configuration
@EnableCaching
public class CachingConfig {
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate){
        return new RedisCacheManager(redisTemplate);
    }

}
