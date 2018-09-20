package com.jimbolix.entity;

import lombok.Data;

import java.io.Serializable;
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
public class ProductInfo implements Serializable {

    private String productId;

    private String productName;

    private double productPrice;

    private Integer productStock;

    private String productDescription;

    private String productIcon;

    private Integer productStatus;

    private Integer categoryType;

    private Date createTime;

    private Date updateTime;
}
