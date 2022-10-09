package com.hkk.demo.mapstruct;

import org.mapstruct.Mapper;

@Mapper
public interface CarMapper {

    CarDto carToCarDto(Car car);

    // 如果CarDto有属性是PersonDto，则会调用
    PersonDto personToPersonDto(Person person);
}