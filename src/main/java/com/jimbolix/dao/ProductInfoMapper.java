package com.jimbolix.dao;

import com.jimbolix.entity.ProductInfo;
import org.apache.ibatis.annotations.Select;

/**
 * @author ruihui.li
 * @version V1.0
 * @Title: springboot
 * @Package com.jimbolix.dao
 * @Description: 使用Mybatis注解的Mapper接口
 * @date 2018/9/18
 */
public interface ProductInfoMapper {

    @Select("select * from Product_info t where t.product_id=#{id}")
    ProductInfo findById(String id);

}
