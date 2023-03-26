package com.xkq.common.to.es;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: xkq
 * @date: 2023/3/25 23:58
 * @description:
 */
@Data
public class SkuEsModel {

    private Long skuId;

    private Long spuId;

    private String skuTitle;

    private BigDecimal skuPrice;

    private String skuImg;

    private Long saleCount;

    private Boolean hasStock;

    private Long hotScore;

    private Long brandId;

    private Long catalogId;

    private String brandName;

    private String brandImg;

    private String catalogName;

    private List<Attr> attrs;

    @Data
    public static class Attr {

        private Long attrId;

        private String attrName;

        private String attrValue;

    }


}
