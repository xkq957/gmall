package com.xkq.gmall.coupon.dao;

import com.xkq.gmall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author xkq
 * @email xu.kq@qq.com
 * @date 2023-03-12 21:22:49
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
