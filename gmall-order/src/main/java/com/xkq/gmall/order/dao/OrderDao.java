package com.xkq.gmall.order.dao;

import com.xkq.gmall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author xkq
 * @email xu.kq@qq.com
 * @date 2023-03-12 21:17:15
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
