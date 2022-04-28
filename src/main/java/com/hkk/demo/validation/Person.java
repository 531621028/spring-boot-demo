package com.hkk.demo.validation;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Person {

    // 错误消息message是可以自定义的
    @NotNull(message = "名字不能为null")
    public String name;
    @Positive
    public Integer age;

    @NotNull
    @NotEmpty
    private List<@Email String> emails;
    @Future
    private Date start;

}
