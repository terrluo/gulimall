package com.atguigu.gulimall.ware.dao;

import com.atguigu.gulimall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 * 
 * @author leifengyang
 * @email leifengyang@gmail.com
 * @date 2025-08-10 17:44:14
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {
	
}
