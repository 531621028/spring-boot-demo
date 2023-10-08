package com.hkk.demo.repository.mapper;

import com.hkk.demo.dto.Goods;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsMapper {

    Goods selectOneGoods(Long goodsId);

    int insertOneGoods(Goods goods);

    int updateOneGoods(Goods goods);
}