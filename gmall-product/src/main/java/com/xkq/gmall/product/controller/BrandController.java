package com.xkq.gmall.product.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.xkq.common.valid.AddGroup;
import com.xkq.common.valid.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xkq.gmall.product.entity.BrandEntity;
import com.xkq.gmall.product.service.BrandService;
import com.xkq.common.utils.PageUtils;
import com.xkq.common.utils.R;

import javax.validation.Valid;
import javax.validation.groups.Default;


/**
 * 品牌
 *
 * @author xkq
 * @email xu.kq@qq.com
 * @date 2023-03-12 21:13:42
 */
@RefreshScope
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @Value("${product.user.name}")
    private String name;

    @Value("${product.user.age}")
    private String age;

    @RequestMapping("/test")
    public R test(){
        return R.ok().put("name", name).put("age", age);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:brand:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    //@RequiresPermissions("product:brand:info")
    public R info(@PathVariable("brandId") Long brandId){
		BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:brand:save")
    public R save (@RequestBody @Validated({AddGroup.class, Default.class}) BrandEntity brand/*, BindingResult result*/){
//        if (result.hasErrors()) {
//            Map<String, String> map = new HashMap<>();
//            //获取校验的错误结果
//            result.getFieldErrors().forEach((item)->{
//                //FiledError 获取到校验提示
//                String defaultMessage = item.getDefaultMessage();
//                //获取错误属性名字
//                String field = item.getField();
//                map.put(defaultMessage, field);
//            });
//            return R.error(400,"提交的数据不合法").put("data", map);
//        }else {
//            brandService.save(brand);
//        }
        brandService.save(brand);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:brand:update")
    public R update(@RequestBody @Validated({UpdateGroup.class, Default.class}) BrandEntity brand){
//		brandService.updateById(brand);
        //级联更新，同时更新品牌-分类关系表里的品牌名
        brandService.updateDetail(brand);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:brand:delete")
    public R delete(@RequestBody Long[] brandIds){
		brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
