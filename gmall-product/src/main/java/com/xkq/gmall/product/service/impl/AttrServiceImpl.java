package com.xkq.gmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xkq.common.constant.ProductConstant;
import com.xkq.gmall.product.dao.AttrAttrgroupRelationDao;
import com.xkq.gmall.product.dao.AttrGroupDao;
import com.xkq.gmall.product.dao.CategoryDao;
import com.xkq.gmall.product.entity.AttrAttrgroupRelationEntity;
import com.xkq.gmall.product.entity.AttrGroupEntity;
import com.xkq.gmall.product.entity.CategoryEntity;
import com.xkq.gmall.product.service.CategoryService;
import com.xkq.gmall.product.vo.AttrGroupRelationVo;
import com.xkq.gmall.product.vo.AttrRspVo;
import com.xkq.gmall.product.vo.AttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkq.common.utils.PageUtils;
import com.xkq.common.utils.Query;

import com.xkq.gmall.product.dao.AttrDao;
import com.xkq.gmall.product.entity.AttrEntity;
import com.xkq.gmall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Resource
    AttrAttrgroupRelationDao relationDao;
    @Resource
    CategoryDao categoryDao;
    @Resource
    AttrGroupDao attrGroupDao;
    @Resource
    CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveAttr(AttrVo attr) {
        //1.保存属性基本数据
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        baseMapper.insert(attrEntity);
        //2.保存关联关系
        if (attr.getAttrType().equals(ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode())) {
            //销售属性才有分组关联
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrId(attrEntity.getAttrId());
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationDao.insert(relationEntity);
        }
    }

    /**
     * 属性列表查询
     * @param params
     * @param catelogId
     * @param attrType 属性类型
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId, Integer attrType) {
        //select * from pms_attr where catelog_id=? and (attr_id=key or attr_name like %key%)
        String key = (String) params.get("key");

        //查询条件
        LambdaQueryWrapper<AttrEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AttrEntity::getAttrType, attrType);
        wrapper.eq(null != catelogId && 0 != catelogId, AttrEntity::getCatelogId, catelogId);
        if (StringUtils.hasLength(key)) {
            wrapper.and(item ->
                    item.eq(AttrEntity::getAttrId, key)
                            .or().like(AttrEntity::getAttrName, key));
        }

        //分页
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), wrapper);
        List<AttrEntity> records = page.getRecords();
        //获取所有分类id，批量查询分类名
        List<Long> catelogList = records.stream().map(AttrEntity::getCatelogId).collect(Collectors.toList());
        List<CategoryEntity> categoryEntities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(catelogList)) {
            categoryEntities = categoryDao.selectBatchIds(catelogList);
        }
        List<CategoryEntity> finalCategoryEntities = categoryEntities;

        List<AttrRspVo> rspVos = new ArrayList<>();

        //获取所有属性id，批量查询分组关联关系，通过关联关系查询所有分组
        if (attrType.equals(ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode())) {
            //只有基本属性才有分组
            List<Long> attrIdList = records.stream().map(AttrEntity::getAttrId).collect(Collectors.toList());
            List<AttrAttrgroupRelationEntity> relationEntityList = relationDao.selectList(new LambdaUpdateWrapper<AttrAttrgroupRelationEntity>().in(AttrAttrgroupRelationEntity::getAttrId, attrIdList));
//        List<AttrAttrgroupRelationEntity> relationEntityList = relationDao.selectBatchAttrIds(attrIdList);
            List<Long> attrGroupIdList = relationEntityList.stream().map(AttrAttrgroupRelationEntity::getAttrGroupId).collect(Collectors.toList());
            List<AttrGroupEntity> attrGroupEntityList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(attrGroupIdList)) {
                attrGroupEntityList = attrGroupDao.selectBatchIds(attrGroupIdList);
            }//将catelogName和groupName set至records中
            //分组信息转map
            List<AttrGroupEntity> finalAttrGroupEntityList = attrGroupEntityList;
            Map<Long, String> attrGroupNameMap = relationEntityList.stream()
                    .collect(Collectors.toMap(AttrAttrgroupRelationEntity::getAttrId,
                            relation -> finalAttrGroupEntityList.stream()
                                    .filter(group -> group.getAttrGroupId().equals(relation.getAttrGroupId()))
                                    .findFirst()
                                    .map(AttrGroupEntity::getAttrGroupName)
                                    .orElse("")));
            //设置分组、分类信息
            records.forEach(item -> {
                AttrRspVo attrRspVo = new AttrRspVo();
                BeanUtils.copyProperties(item, attrRspVo);
                attrRspVo.setGroupName(attrGroupNameMap.get(attrRspVo.getAttrId()));
                attrRspVo.setCatelogName(finalCategoryEntities.stream()
                        .filter(categoryEntity -> categoryEntity.getCatId().equals(attrRspVo.getCatelogId()))
                        .map(CategoryEntity::getName)
                        .findFirst()
                        .orElse(""));
                rspVos.add(attrRspVo);
            });
        }else {
            //销售属性不用查询分组关系
            records.forEach(item -> {
                AttrRspVo attrRspVo = new AttrRspVo();
                BeanUtils.copyProperties(item, attrRspVo);
                attrRspVo.setCatelogName(finalCategoryEntities.stream()
                        .filter(categoryEntity -> categoryEntity.getCatId().equals(attrRspVo.getCatelogId()))
                        .map(CategoryEntity::getName)
                        .findFirst()
                        .orElse(""));
                rspVos.add(attrRspVo);
            });
        }

        PageUtils pageUtils = new PageUtils(page);
        pageUtils.setList(rspVos);
        return pageUtils;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
        return null;
    }

    @Override
    public AttrRspVo getDetail(Long attrId) {
        AttrEntity attrEntity = baseMapper.selectById(attrId);
        AttrRspVo attrRspVo = new AttrRspVo();
        BeanUtils.copyProperties(attrEntity, attrRspVo);

        //设置分组信息
        if (attrEntity.getAttrType().equals(ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode())) {
            LambdaQueryWrapper<AttrAttrgroupRelationEntity> wrapper = new LambdaQueryWrapper<>();
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = relationDao.selectOne(wrapper.eq(AttrAttrgroupRelationEntity::getAttrId, attrId));
            if (Objects.nonNull(attrAttrgroupRelationEntity)) {
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrAttrgroupRelationEntity.getAttrGroupId());
                attrRspVo.setGroupName(attrGroupEntity.getAttrGroupName());
                attrRspVo.setAttrGroupId(attrGroupEntity.getAttrGroupId());
            }
        }

        //设置分类信息
        Long[] categoryPath = categoryService.findCategoryPath(attrEntity.getCatelogId());
        attrRspVo.setCatelogPath(categoryPath);
        CategoryEntity entity = categoryDao.selectById(attrEntity.getCatelogId());
        if (Objects.nonNull(entity)) {
            attrRspVo.setCatelogName(entity.getName());
        }

        return attrRspVo;
    }

    /**
     * 修改属性，同时修改属性分组关联
     * @param attr
     */
    @Transactional
    @Override
    public void updateAttr(AttrVo attr) {
        // 1.修改属性
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        baseMapper.updateById(attrEntity);

        Long attrId = attr.getAttrId();
        //2.修改属性分组关联
        if (attr.getAttrType().equals(ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode())) {
            Integer relationCount = relationDao.selectCount(new LambdaQueryWrapper<AttrAttrgroupRelationEntity>().eq(AttrAttrgroupRelationEntity::getAttrId, attrId));
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrId(attrId);
            attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
            if (relationCount > 0) {
                //已有修改
                relationDao.update(attrAttrgroupRelationEntity, new LambdaUpdateWrapper<AttrAttrgroupRelationEntity>().eq(AttrAttrgroupRelationEntity::getAttrId, attrId));
            }else {
                //没有新增
                relationDao.insert(attrAttrgroupRelationEntity);
            }
        }
    }

    /**
     * 根据分组id查找关联的所有基本属性
     * @param attrgroupId
     * @return
     */
    @Override
    public List<AttrEntity> getRelationAttr(Long attrgroupId) {
        //查询所有关联关系
        LambdaQueryWrapper<AttrAttrgroupRelationEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AttrAttrgroupRelationEntity::getAttrGroupId, attrgroupId);
        List<AttrAttrgroupRelationEntity> relationEntities = relationDao.selectList(wrapper);

        List<Long> attrIdList = relationEntities.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());

        //根据id查询所有属性
        if (!CollectionUtils.isEmpty(attrIdList)) {
            return baseMapper.selectBatchIds(attrIdList);
        }

        return null;
    }

    /**
     * 根据分组id查找未关联的所有基本属性
     * @param attrgroupId
     * @return
     */
    @Override
    public PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId) {
        //1、当前分组只能关联自己所属的分类里面的所有属性
        AttrEntity attrEntity = baseMapper.selectById(attrgroupId);
        Long categoryId = attrEntity.getCatelogId();

        //2、当前分组只能关联别的分组没有引用的属性
        //2.1)、当前分类下的其他分组
        List<AttrGroupEntity> otherGroup = attrGroupDao.selectList(new LambdaUpdateWrapper<AttrGroupEntity>().eq(AttrGroupEntity::getCatelogId, categoryId));
        List<Long> otherGroupId = otherGroup.stream().map(AttrGroupEntity::getAttrGroupId).collect(Collectors.toList());

        //2.2)、这些分组关联的属性
        List<AttrAttrgroupRelationEntity> relationEntities = relationDao.selectList(new LambdaUpdateWrapper<AttrAttrgroupRelationEntity>().in(AttrAttrgroupRelationEntity::getAttrGroupId, otherGroupId));
        List<Long> otherAttrId = relationEntities.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());
        //2.3)、从当前分类的所有属性中移除这些属性；
        String key = (String) params.get("key");
        LambdaUpdateWrapper<AttrEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AttrEntity::getCatelogId, categoryId)
                .eq(AttrEntity::getAttrType, ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode())
                .notIn(!CollectionUtils.isEmpty(otherAttrId), AttrEntity::getAttrId, otherAttrId);
        if (StringUtils.hasLength(key)) {
            wrapper.and(w -> w.eq(AttrEntity::getAttrId, key).or().like(AttrEntity::getAttrName, key));
        }

        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), wrapper);

        PageUtils pageUtils = new PageUtils(page);

        return pageUtils;
    }

    @Override
    public List<Long> selectSearchAttrIds(List<Long> attrIds) {
        return baseMapper.selectSearchAttrIds(attrIds);
    }

}