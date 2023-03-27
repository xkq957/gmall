package com.xkq.gmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xkq.common.utils.PageUtils;
import com.xkq.gmall.product.entity.CategoryEntity;
import com.xkq.gmall.product.vo.Catalog2Vo;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author xkq
 * @email xu.kq@qq.com
 * @date 2023-03-12 21:13:42
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> listWithTree();

    Long[] findCategoryPath(Long categoryId);

    void updateDetail(CategoryEntity category);

    List<CategoryEntity> getLevel1Catagories();

    Map<String, List<Catalog2Vo>> getCatalogJsonDbWithSpringCache();
}

