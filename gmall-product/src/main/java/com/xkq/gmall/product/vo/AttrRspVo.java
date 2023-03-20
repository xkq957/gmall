package com.xkq.gmall.product.vo;

import lombok.Data;

/**
 * @author: xkq
 * @date: 2023/3/19 10:27
 * @description:
 */
@Data
public class AttrRspVo extends AttrVo{
    /**
     * 			"catelogName": "手机/数码/手机", //所属分类名字
     * 			"groupName": "主体", //所属分组名字
     */
    private String catelogName;
    private String groupName;

    private Long[] catelogPath;
}
