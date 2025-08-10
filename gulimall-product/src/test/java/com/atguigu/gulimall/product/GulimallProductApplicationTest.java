package com.atguigu.gulimall.product;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.atguigu.gulimall.product.entity.BrandEntity;
import com.atguigu.gulimall.product.service.BrandService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GulimallProductApplicationTest {
    @Autowired
    BrandService brandService;

    @Test
    public void contextLoads() {
        // BrandEntity brandEntity = new BrandEntity();
        // brandEntity.setName("华为");
        // brandService.save(brandEntity);
        // brandEntity.setBrandId(1L);
        // brandEntity.setDescript("华为手机");
        // brandService.updateById(brandEntity);
        // System.out.println("保存成功");

        List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 1L));
        list.forEach((item) -> {
            System.out.println(item);
        });
    }
}
