package com.xkq.gmall.product.feign;

import com.xkq.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author: xkq
 * @date: 2023/3/26 0:41
 * @description:
 */
@FeignClient("gmall-ware")
public interface FeignWareService {
    @RequestMapping("/ware/waresku/getSkuHasStocks")
    R getSkuHasStocks(@RequestBody List<Long> ids);
}
