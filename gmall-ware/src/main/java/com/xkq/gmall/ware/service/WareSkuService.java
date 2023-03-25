package com.xkq.gmall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xkq.common.to.SkuHasStockVo;
import com.xkq.common.utils.PageUtils;
import com.xkq.gmall.ware.entity.WareSkuEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author xkq
 * @email xu.kq@qq.com
 * @date 2023-03-12 21:15:33
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addStock(Long skuId, Long wareId, Integer skuNum);

    List<SkuHasStockVo> getSkuHasStocks(List<Long> ids);
}

