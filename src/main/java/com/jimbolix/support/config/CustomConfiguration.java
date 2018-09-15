package com.jimbolix.support.config;

import com.jimbolix.support.componet.CustomLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

/**
 * @author ruihui.li
 * @version V1.0
 * @Title: springboot
 * @Package com.jimbolix.support.config
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @date 2018/9/13
 */
@Configuration
public class CustomConfiguration {

    @Bean
    public LocaleResolver localeResolver(){
        return new CustomLocaleResolver();
    }
}
