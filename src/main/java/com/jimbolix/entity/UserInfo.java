package com.jimbolix.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author ruihui.li
 * @version V1.0
 * @Title: springboot
 * @Package com.jimbolix.entity
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @date 2018/9/18
 */
@Data
public class UserInfo {

    private String id;

    private String userName;

    private String passWord;

    private String openId;

    private Integer role;//1买家2卖家

    private Date createTime;

    private Date updateTime;
}
