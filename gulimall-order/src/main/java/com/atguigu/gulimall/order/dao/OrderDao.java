package com.atguigu.gulimall.order.dao;

import com.atguigu.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author leifengyang
 * @email leifengyang@gmail.com
 * @date 2025-08-10 17:41:43
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
