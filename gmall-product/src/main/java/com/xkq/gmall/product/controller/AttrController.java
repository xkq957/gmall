package com.xkq.gmall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.xkq.common.constant.ProductConstant;
import com.xkq.gmall.product.entity.ProductAttrValueEntity;
import com.xkq.gmall.product.service.CategoryService;
import com.xkq.gmall.product.service.ProductAttrValueService;
import com.xkq.gmall.product.vo.AttrRspVo;
import com.xkq.gmall.product.vo.AttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xkq.gmall.product.entity.AttrEntity;
import com.xkq.gmall.product.service.AttrService;
import com.xkq.common.utils.PageUtils;
import com.xkq.common.utils.R;

import javax.annotation.Resource;


/**
 * 商品属性
 *
 * @author xkq
 * @email xu.kq@qq.com
 * @date 2023-03-12 21:13:42
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;
    @Resource
    CategoryService categoryService;
    @Autowired
    ProductAttrValueService productAttrValueService;

    /**
     * 获取spu规格
     * @param spuId
     * @return
     */
    // /product/attr/base/listforspu/{spuId}
    @GetMapping("/base/listforspu/{spuId}")
    public R baseAttrlistforspu(@PathVariable("spuId") Long spuId){

        List<ProductAttrValueEntity> entities = productAttrValueService.baseAttrlistforspu(spuId);

        return R.ok().put("data",entities);
    }

    /**
     * 修改商品规格
     * @param spuId
     * @param entities
     * @return
     */
    ///product/attr/update/{spuId}
    @PostMapping("/update/{spuId}")
    public R updateSpuAttr(@PathVariable("spuId") Long spuId,
                           @RequestBody List<ProductAttrValueEntity> entities){

        productAttrValueService.updateSpuAttr(spuId,entities);

        return R.ok();
    }

    /**
     * 查询规格参数列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:attr:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 基本属性列表
     */
    @RequestMapping("/base/list/{catelogId}")
    //@RequiresPermissions("product:attr:list")
    public R baseList(@RequestParam Map<String, Object> params, @PathVariable Long catelogId){
        PageUtils page = attrService.queryPage(params, catelogId, ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());

        return R.ok().put("page", page);
    }

    /**
     * 销售属性列表
     */
    @RequestMapping("/sale/list/{catelogId}")
    //@RequiresPermissions("product:attr:list")
    public R saleList(@RequestParam Map<String, Object> params, @PathVariable Long catelogId){
        PageUtils page = attrService.queryPage(params, catelogId, ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    //@RequiresPermissions("product:attr:info")
    public R info(@PathVariable("attrId") Long attrId){
//		AttrEntity attr = attrService.getById(attrId);
//        Long[] categoryPath = categoryService.findCategoryPath(attr.getCatelogId());
//        AttrRspVo attrRspVo = new AttrRspVo();
//        BeanUtils.copyProperties(attr, attrRspVo);
//        attrRspVo.setCatelogPath(categoryPath);
        AttrRspVo attrRspVo = attrService.getDetail(attrId);

        return R.ok().put("attr", attrRspVo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attr:save")
    public R save(@RequestBody AttrVo attr){
//		attrService.save(attr);
        attrService.saveAttr(attr);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attr:update")
    public R update(@RequestBody AttrVo attr){
//		attrService.updateById(attr);
        attrService.updateAttr(attr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attr:delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
