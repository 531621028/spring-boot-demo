package com.hkk.demo.mapstruct;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author kang
 * @date 2022/9/30
 */
@Mapper
public interface PersonMapper {
    PersonMapper mapper = Mappers.getMapper(PersonMapper.class);

    @Mapping(target = "id", source = "person.id")
    PersonDTO toDto(Person person, Address address);

}
