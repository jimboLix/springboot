package com.jimbolix.support.initializer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;

/**
 * @author ruihui.li
 * @version V1.0
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @date 2018/9/10
 */
@Slf4j
public class CustomContextInitializerOrderLater implements ApplicationContextInitializer<ConfigurableApplicationContext>,Ordered {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        log.info("顺序上第二个自定义的上下文初始化器执行，上下文对象Id：{}",applicationContext.getId());
        log.info("ConfigurableApplicationContext对象是{}",applicationContext);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE - 2;
    }
}
