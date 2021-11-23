package com.hkk.demo.generic;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GenericDemo {


    public static Class<?> getGenericClass(Class<?> clazz) {
        Type type = clazz.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return (Class<?>) parameterizedType.getActualTypeArguments()[0];
        }
        return null;
    }

    public static Type getParameterGenericClass(Class<?> clazz)
        throws NoSuchFieldException {
        Field field = clazz.getField("data");
        if (field.getGenericType() instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
            return parameterizedType.getActualTypeArguments()[0];
        }
        return null;
    }


    public static void main(String[] args) throws NoSuchFieldException {
        List<DemoSheet> demoSheets = new ArrayList<>();
        List<? extends Sheet<Integer>> sheets = demoSheets;
        //        泛型类型的子类型的不相关性；? extends 是泛型类型的子类型相关性成为现实，? super 是泛型类型的父类型相关性成为现实
//         PECS：指“Producer Extends，Consumer Super”。换句话说，如果参数化类型表示一个生产者，就使用<? extends T>；如果它表示一个消费者，就使用<? super T>
//        如果你想从一个数据类型里获取数据，使用 ? extends 通配符
//        如果你想把对象写入一个数据结构里，使用 ? super 通配符
//        如果你既想存，又想取，那就别用通配符。
        Component<Integer> component = new Component<>();
        component.setSheets(demoSheets);
        // 因为DemoSheet继承自Sheet<Integer>，所以可以通过getGenericClass获取到泛型的类型Integer
        // Component<Integer> 只是实例化了自己的类型，他的父类使Object，没有泛型参数，拿不到泛型参数Integer
        System.out.println(getGenericClass(DemoSheet.class));
        System.out.println(getGenericClass(component.getClass()));
        // 获取到父类data指定的泛型化参数是T
        System.out.println(getParameterGenericClass(DemoSheet.class));
    }


    public static class DemoSheet extends Sheet<Integer> {

//        public List<Integer> data;
    }

    public static class Sheet<T> {

        public List<T> data = new ArrayList<>();

    }

    public static class Component<T> {

        List<? extends Sheet<? extends T>> sheets;

        public void setSheets(
            List<? extends Sheet<? extends T>> sheets) {
            this.sheets = sheets;
        }
    }

}
