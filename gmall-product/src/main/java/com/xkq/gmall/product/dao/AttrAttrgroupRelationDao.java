package com.xkq.gmall.product.dao;

import com.xkq.gmall.product.entity.AttrAttrgroupRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性&属性分组关联
 * 
 * @author xkq
 * @email xu.kq@qq.com
 * @date 2023-03-12 21:13:42
 */
@Mapper
public interface AttrAttrgroupRelationDao extends BaseMapper<AttrAttrgroupRelationEntity> {

    List<AttrAttrgroupRelationEntity> selectBatchAttrIds(@Param("list") List<Long> attrIdList);

    void deleteBatchRelation(@Param("list") List<AttrAttrgroupRelationEntity> entities);
}
