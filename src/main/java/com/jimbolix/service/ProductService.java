package com.jimbolix.service;

import com.jimbolix.entity.ProductInfo;

/**
 * @author ruihui.li
 * @version V1.0
 * @Title: springboot
 * @Package com.jimbolix.service
 * @Description:
 * @date 2018/9/19
 */
public interface ProductService {

    ProductInfo findById(String id);
}
