package com.hkk.demo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class Goods {

    //商品id
    @TableId(value = "goods_id", type = IdType.AUTO)
    Long goodsId;
    //商品名称
    private String goodsName;
    //商品标题
    private String subject;
    //商品价格
    private BigDecimal price;
    //库存
    int stock;

}