package com.xkq.gmall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Catalog2Vo {
    //父分类id
    private String catalog1Id;

    private String id;

    private String name;

    /**
     * 三级子分类
     */
    private List<Catalog3Vo> catalog3List;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Catalog3Vo{
        //父分类：2级分类菜单
        private String catalog2Id;
        private String id;
        private String name;
    }
}
