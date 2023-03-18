package com.xkq.gmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkq.common.utils.PageUtils;
import com.xkq.common.utils.Query;

import com.xkq.gmall.product.dao.AttrGroupDao;
import com.xkq.gmall.product.entity.AttrGroupEntity;
import com.xkq.gmall.product.service.AttrGroupService;
import org.springframework.util.StringUtils;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catId) {

        //select * from pms_attr_group where catelog_id=? and (attr_group_id=key or attr_group_name like %key%)
        String key = (String) params.get("key");

        //查询条件
        LambdaQueryWrapper<AttrGroupEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(null != catId && 0 != catId, AttrGroupEntity::getCatelogId, catId);
        if (StringUtils.hasLength(key)) {
            wrapper.and((item)->{
                item.eq(AttrGroupEntity::getAttrGroupId, key)
                        .or().like(AttrGroupEntity::getAttrGroupName, key);
            });
        }

        //分页
        IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);

        return new PageUtils(page);
    }

}