package com.jimbolix.support.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;

/**
 * @author ruihui.li
 * @version V1.0
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @date 2018/9/10
 */
@Slf4j
public class CustomApplicationListenerLater implements ApplicationListener<SpringApplicationEvent>,Ordered{
    @Override
    public void onApplicationEvent(SpringApplicationEvent event) {
        log.info("自定义的第二个应用监听器执行，应用是{}，事件时间是{}",event.getSpringApplication(),event.getTimestamp());
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
