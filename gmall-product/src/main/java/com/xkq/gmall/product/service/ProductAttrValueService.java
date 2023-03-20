package com.xkq.gmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xkq.common.utils.PageUtils;
import com.xkq.gmall.product.entity.ProductAttrValueEntity;

import java.util.List;
import java.util.Map;

/**
 * spu属性值
 *
 * @author xkq
 * @email xu.kq@qq.com
 * @date 2023-03-12 21:13:42
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveProductAttr(List<ProductAttrValueEntity> collect);
}

