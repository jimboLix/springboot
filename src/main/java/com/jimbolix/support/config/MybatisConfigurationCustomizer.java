package com.jimbolix.support.config;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ruihui.li
 * @version V1.0
 * @Title: springboot
 * @Package com.jimbolix.support.config
 * @Description: Mybatis的一些配置
 * @date 2018/9/18
 */
@Configuration
@MapperScan(basePackages = {"com.jimbolix.dao"})
@EnableCaching
public class MybatisConfigurationCustomizer {

    /**
     * 开启驼峰属性映射
     * product_info -> productInfo
     * @return
     */
    @Bean
    public ConfigurationCustomizer customizer(){
        return new ConfigurationCustomizer() {
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                configuration.setMapUnderscoreToCamelCase(true);
            }
        };
    }
}
