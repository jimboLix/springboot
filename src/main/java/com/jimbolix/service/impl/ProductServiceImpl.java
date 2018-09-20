package com.jimbolix.service.impl;

import com.jimbolix.dao.ProductInfoMapper;
import com.jimbolix.entity.ProductInfo;
import com.jimbolix.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ruihui.li
 * @version V1.0
 * @Title: springboot
 * @Package com.jimbolix.service.impl
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @date 2018/9/19
 */
@Slf4j
@Service
@Transactional
@CacheConfig(cacheManager = "productCacheManager")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Cacheable(cacheNames = {"product"},keyGenerator = "keyGeneratorCustomier",unless = "#result == null")
    @Transactional(readOnly = true)
    @Override
    public ProductInfo findById(String id) {
        log.info("执行商品查询");
        return productInfoMapper.findById(id);
    }
}
