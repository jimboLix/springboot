package com.jimbolix.dao;

import com.jimbolix.entity.UserInfo;

/**
 * @author ruihui.li
 * @version V1.0
 * @Title: springboot
 * @Package com.jimbolix.dao
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @date 2018/9/18
 */
public interface UserMapper {

    UserInfo findById(String id);
}
