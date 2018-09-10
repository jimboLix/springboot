package com.jimbolix.support.initializer;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author ruihui.li
 * @version V1.0
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @date 2018/9/10
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomContextInitializerOrderBefore implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

    }
}
