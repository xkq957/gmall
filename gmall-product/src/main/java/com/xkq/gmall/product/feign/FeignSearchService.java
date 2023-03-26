package com.xkq.gmall.product.feign;

import com.xkq.common.to.es.SkuEsModel;
import com.xkq.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author: xkq
 * @date: 2023/3/26 0:44
 * @description:
 */
@FeignClient("gmall-search")
public interface FeignSearchService {
    @PostMapping("/search/save/product")
    R saveProductAsIndices(@RequestBody List<SkuEsModel> skuEsModels);
}
