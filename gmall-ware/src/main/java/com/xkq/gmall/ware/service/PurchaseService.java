package com.xkq.gmall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xkq.common.utils.PageUtils;
import com.xkq.gmall.ware.entity.PurchaseEntity;
import com.xkq.gmall.ware.vo.MergeVo;
import com.xkq.gmall.ware.vo.PurchaseDoneVo;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author xkq
 * @email xu.kq@qq.com
 * @date 2023-03-12 21:15:33
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageUnreceivePurchase(Map<String, Object> params);

    void mergePurchase(MergeVo mergeVo);

    void received(List<Long> ids);

    void done(PurchaseDoneVo doneVo);
}

