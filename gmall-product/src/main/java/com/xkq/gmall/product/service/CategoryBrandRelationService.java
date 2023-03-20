package com.xkq.gmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xkq.common.utils.PageUtils;
import com.xkq.gmall.product.entity.BrandEntity;
import com.xkq.gmall.product.entity.CategoryBrandRelationEntity;
import com.xkq.gmall.product.vo.BrandVo;

import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author xkq
 * @email xu.kq@qq.com
 * @date 2023-03-12 21:13:42
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveDetail(CategoryBrandRelationEntity categoryBrandRelation);

    void updateBrand(Long brandId, String name);

    void updateCategory(Long catId, String name);

    List<BrandEntity> getBrandsByCatId(Long catId);
}

