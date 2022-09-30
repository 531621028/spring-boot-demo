package com.hkk.demo.mapstruct;

/**
 * @author kang
 * @date 2022/9/30
 */
public class MapStructTest {

    public static void main(String[] args) {
        PersonMapper.mapper.toDto(new Person(), new Address());
    }

}
