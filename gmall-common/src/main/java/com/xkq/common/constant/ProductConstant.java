package com.xkq.common.constant;

/**
 * @author: xkq
 * @date: 2023/3/19 15:35
 * @description:
 */
public class ProductConstant {

    /**
     * 属性分类
     */
    public enum AttrEnum {
        ATTR_TYPE_BASE(1,"基本属性")
        ,ATTR_TYPE_SALE(0,"销售属性");
        private int code;
        private String msg;

        AttrEnum(int code,String msg){
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }
}
