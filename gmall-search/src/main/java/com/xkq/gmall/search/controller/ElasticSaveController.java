package com.xkq.gmall.search.controller;

import com.xkq.common.exception.BizCodeEnum;
import com.xkq.common.to.es.SkuEsModel;
import com.xkq.common.utils.R;
import com.xkq.gmall.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: xkq
 * @date: 2023/3/26 0:51
 * @description:
 */
@Slf4j
@RequestMapping(value = "/search/save")
@RestController
public class ElasticSaveController {
    @Autowired
    private ProductSaveService productSaveService;

    @PostMapping("/product")
    public R saveProductAsIndices(@RequestBody List<SkuEsModel> skuEsModels) {
        boolean status = false;
        try {
            status=productSaveService.saveProductAsIndices(skuEsModels);
        } catch (Exception e) {
            log.error("远程保存索引失败");
        }
        if (status){
            return R.ok();
        }else {
            return R.error(BizCodeEnum.PRODUCT_UP_EXCEPTION.getCode(), BizCodeEnum.PRODUCT_UP_EXCEPTION.getMsg());
        }

    }
}
