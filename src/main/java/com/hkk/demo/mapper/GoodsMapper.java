package com.hkk.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hkk.demo.dto.Goods;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

}