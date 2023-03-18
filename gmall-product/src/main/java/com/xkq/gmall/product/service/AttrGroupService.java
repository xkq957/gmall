package com.xkq.gmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xkq.common.utils.PageUtils;
import com.xkq.gmall.product.entity.AttrGroupEntity;

import java.util.Map;

/**
 * 属性分组
 *
 * @author xkq
 * @email xu.kq@qq.com
 * @date 2023-03-12 21:13:42
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params, Long catId);
}

