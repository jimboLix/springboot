package com.jimbolix.data;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

/**
 * @author ruihui.li
 * @version V1.0
 * @Title: springboot
 * @Package com.jimbolix.data
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @date 2018/9/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class JdbcTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testDataSource(){
        log.info("数据源类型是{}",dataSource.getClass().getName());
    }
}
