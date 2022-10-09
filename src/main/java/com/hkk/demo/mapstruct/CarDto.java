package com.hkk.demo.mapstruct;

import lombok.Getter;
import lombok.Setter;

/**
 * @author kang
 * @date 2022/9/30
 */
@Setter
@Getter
public class CarDto {

    private String name;
    private PersonDto driver;
}
