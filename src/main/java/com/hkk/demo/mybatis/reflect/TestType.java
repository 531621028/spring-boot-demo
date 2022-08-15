package com.hkk.demo.mybatis.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.apache.ibatis.reflection.TypeParameterResolver;

/**
 * @author kang
 * @date 2022/8/15
 */
public class TestType {

    private SubClassA<Long> aa;

    public static void main(String[] args) throws NoSuchFieldException {
        Field f = ClassA.class.getDeclaredField("map");
        System.out.println(f.getGenericType());
        System.out.println(f.getGenericType() instanceof ParameterizedType);

        final Type type = TypeParameterResolver.resolveFieldType(f,
            TestType.class.getDeclaredField("aa").getGenericType());

        System.out.println(type.getClass());
    }

}
