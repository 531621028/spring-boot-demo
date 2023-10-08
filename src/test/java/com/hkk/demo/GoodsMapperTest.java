package com.hkk.demo;

import com.hkk.demo.dto.Goods;
import com.hkk.demo.repository.mapper.GoodsMapper;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@MybatisTest
public class GoodsMapperTest {

    @Autowired
    private GoodsMapper goodsMapper;

    @Test
    @DisplayName("商品service:得到一件商品")
    public void getOneGoodsById() {
        Goods goodsRet = goodsMapper.selectOneGoods(13L);
        Assert.assertEquals(13L, (long) goodsRet.getGoodsId());
    }

    @Test
    @DisplayName("商品service:测试添加一件商品")
    public void addOneGoods() {
        Goods goodsOne = new Goods();
        //goodsOne.setGoodsId(13L);
        goodsOne.setGoodsName("商品名称");
        goodsOne.setSubject("商品描述");
        goodsOne.setPrice(new BigDecimal(101));
        goodsOne.setStock(13);

        goodsMapper.insertOneGoods(goodsOne);
        Assert.assertEquals(new BigDecimal(101), goodsOne.getPrice());

        Goods goodsRet = goodsMapper.selectOneGoods(14L);
        Assert.assertEquals(14L, (long) goodsRet.getGoodsId());
    }
}