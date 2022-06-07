package com.hkk.demo.reflect;

import java.lang.reflect.Method;

public class ReflectDemo {

    public static void target(int i) {
        new Exception("#" + i).printStackTrace();
    }

    public static void main(String[] args) throws Exception {
        // 在默认情况下，方法的反射调用为委派实现，委派给本地实现来进行方法调用。在调用超过 15 次之后，委派实现便会将委派对象切换至动态实现。这个动态实现的字节码是自动生成的，它将直接使用 invoke 指令来调用目标方法。
        Class<?> klass = Class.forName("com.hkk.demo.reflect.ReflectDemo");
        Method method = klass.getMethod("target", int.class);
        for (int i = 0; i < 20; i++) {
            method.invoke(null, 0);
        }
    }
}