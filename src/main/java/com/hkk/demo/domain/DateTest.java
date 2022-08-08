package com.hkk.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hkk.demo.mapper.handler.MyDateTypeHandler;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DateTest {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    // private String date;
    @TableField(typeHandler = MyDateTypeHandler.class)
    private Date date;
}