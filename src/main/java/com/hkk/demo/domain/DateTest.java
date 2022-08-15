package com.hkk.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hkk.demo.mapper.handler.MyDateTypeHandler;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "date_test", autoResultMap = true)
public class DateTest {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField(typeHandler = MyDateTypeHandler.class)
    private Date date;
}