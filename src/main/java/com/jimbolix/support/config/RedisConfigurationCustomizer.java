package com.jimbolix.support.config;

import com.jimbolix.entity.ProductInfo;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * @author ruihui.li
 * @version V1.0
 * @Title: springboot
 * @Package com.jimbolix.support.config
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @date 2018/9/20
 */
@Configuration
@ConfigurationProperties(prefix = "spring.cache")
public class RedisConfigurationCustomizer {

    /**
     * Entry expiration. By default the entries never expire.
     */
    private Duration timeToLive;

    public Duration getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(Duration timeToLive) {
        this.timeToLive = timeToLive;
    }

    @Bean(value = "productCacheManager")
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        Jackson2JsonRedisSerializer<ProductInfo> serializer = new Jackson2JsonRedisSerializer<ProductInfo>(ProductInfo.class);
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(serializer));
        if(null != timeToLive){
            config.entryTtl(timeToLive);
        }
        RedisCacheManager redisCacheManager = RedisCacheManager.builder(redisConnectionFactory).
                cacheDefaults(config).build();
        return redisCacheManager;

    }
}
