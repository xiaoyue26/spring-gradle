package com.xiaoyue.nov.practice.redis;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by xiaoyue26 on 17/12/4.
 */
@Configuration
public class RedisConfigure {
    /*@Bean
    @Primary
    public RedisConnectionFactory redisCF() {
        JedisConnectionFactory cf = new JedisConnectionFactory();
        cf.setHostName("localhost");
        cf.setPort(6379);
        cf.setPassword("redis123");
        return cf;
    }*/

    @Bean
    public RedisTemplate<String, Product> redisTemplate(RedisConnectionFactory cf) {
        RedisTemplate<String, Product> redis = new RedisTemplate<>();
        redis.setConnectionFactory(cf);
        /*redis.setStringSerializer(new StringRedisSerializer());// 配置非默认的序列化类
        redis.setValueSerializer(new Jackson2JsonRedisSerializer<Product>(Product.class));*/
        return redis;
    }
}

