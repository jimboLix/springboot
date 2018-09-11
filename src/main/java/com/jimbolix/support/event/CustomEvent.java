package com.jimbolix.support.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author ruihui.li
 * @version V1.0
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @date 2018/9/10
 */
@Slf4j
public class CustomEvent {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.addApplicationListener(event -> {
            log.info("监听到事件：{}",event);
        });
        //启动context
        context.refresh();

        context.stop();
    }
}
