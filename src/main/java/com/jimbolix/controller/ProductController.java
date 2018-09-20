package com.jimbolix.controller;

import com.jimbolix.dao.ProductInfoMapper;
import com.jimbolix.entity.ProductInfo;
import com.jimbolix.service.ProductService;
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
 * @Description: 商品的控制层
 * @date 2018/9/18
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ProductInfo getProById(@PathVariable("id") String id){
        return productService.findById(id);
    }
}
