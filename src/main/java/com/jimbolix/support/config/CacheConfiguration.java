package com.jimbolix.support.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ruihui.li
 * @version V1.0
 * @Title: springboot
 * @Package com.jimbolix.support.config
 * @Description: 缓存相关的配置类
 * @date 2018/9/19
 */
@Configuration
public class CacheConfiguration {

    @Bean(value = "keyGeneratorCustomier")
    public KeyGenerator keyGenerator(){
        KeyGenerator keyGenerator = new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder(target.getClass().getCanonicalName());
                sb.append(".").append(method.getName());
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                StringWriter stringWriter = new StringWriter();

                if (null != params && params.length > 0) {
                    for (Object param : params) {
                        try {
                            objectMapper.writeValue(stringWriter, param);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                sb.append(stringWriter.toString());
                return sb.toString();
            }
        };
        return keyGenerator;
    }
}
