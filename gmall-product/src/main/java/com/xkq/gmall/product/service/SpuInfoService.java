package com.xkq.gmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xkq.common.utils.PageUtils;
import com.xkq.gmall.product.entity.SpuInfoEntity;
import com.xkq.gmall.product.vo.SpuSaveVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author xkq
 * @email xu.kq@qq.com
 * @date 2023-03-12 21:13:42
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfo(SpuSaveVo vo);

    PageUtils queryPageByCondition(Map<String, Object> params);

    void up(Long spuId);
}

