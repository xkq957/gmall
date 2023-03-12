package com.xkq.gmall.product.dao;

import com.xkq.gmall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author xkq
 * @email xu.kq@qq.com
 * @date 2023-03-12 21:13:42
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
