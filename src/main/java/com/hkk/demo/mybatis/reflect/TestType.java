package com.hkk.demo.mybatis.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import org.apache.ibatis.reflection.TypeParameterResolver;

/**
 * @author kang
 * @date 2022/8/15
 */
public class TestType {

    private SubClassA<Long> aa;
    static Number method(Number num) {
        return 1;
    }



    public static void main(String[] args) throws NoSuchFieldException {


        // Integer.class.isAssignableFrom(Number.class) == false
        // System.out.println(Integer.class.isAssignableFrom(Number.class));
        Field f = ClassA.class.getDeclaredField("map");
        // System.out.println(f.getGenericType());
        // System.out.println(f.getGenericType() instanceof ParameterizedType);

        final Type type = TypeParameterResolver.resolveFieldType(f,
            TestType.class.getDeclaredField("aa").getGenericType());
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            final Type[] actualTypes = parameterizedType.getActualTypeArguments();
            for (Type t : actualTypes) {
                System.out.println(t);
            }
        }
    }

}
