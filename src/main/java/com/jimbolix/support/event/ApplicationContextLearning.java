package com.jimbolix.support.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author ruihui.li
 * @version V1.0
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @date 2018/9/11
 */
@SpringBootApplication
@Slf4j
public class ApplicationContextLearning {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(ApplicationContextLearning.class)
                .web(WebApplicationType.SERVLET).run(args);
        log.info("上下文对象的类型是：【{}】",context);
    }
}
