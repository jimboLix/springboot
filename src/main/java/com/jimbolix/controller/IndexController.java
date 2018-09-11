package com.jimbolix.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ruihui.li
 * @version V1.0
 * @Description: 控制器测试
 * @date 2018/9/11
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    @RequestMapping("/index")
    public String index(){
        return "index";
    }
}
