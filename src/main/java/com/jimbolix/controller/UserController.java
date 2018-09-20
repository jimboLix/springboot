package com.jimbolix.controller;

import com.jimbolix.dao.UserMapper;
import com.jimbolix.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ruihui.li
 * @version V1.0
 * @Title: springboot
 * @Package com.jimbolix.controller
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @date 2018/9/18
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/{id}")
    public UserInfo getById(@PathVariable(value = "id") String id){
        return  userMapper.findById(id);
    }
}
