package com.hkk.demo.mapstruct;

import lombok.Getter;
import lombok.Setter;

/**
 * @author kang
 * @date 2022/9/30
 */
@Setter
@Getter
public class Car {

    private Person driver;
    private String name;
}
