package com.hkk.demo.generic;

public class GenericTest {

    public static class A {

        public A get() {
            return null;
        }
    }

    public static class B extends A {

        @Override
        public B get() {
            return null;
        }
    }

    public static class C extends A {

        @Override
        public C get() {
            return null;
        }
    }

    public static class CC extends C {

        @Override
        public CC get() {
            return new CC();
        }
    }

}
