package com.jimbolix.support.config;

import com.jimbolix.support.interceptor.CustomHandlerInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ruihui.li
 * @version V1.0
 * @Description:
 * @date 2018/9/11
 */
@Configuration
public class McvConfiger implements WebMvcConfigurer{

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CustomHandlerInterceptor())
        .addPathPatterns("/index/*")
        ;
    }
}
