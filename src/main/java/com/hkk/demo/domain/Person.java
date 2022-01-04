package com.hkk.demo.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Person
 *
 * @author hukangkang
 * @since 2021/6/30
 */
@Setter
@Getter
public class Person {

    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person() {

    }

    @Override
    public String toString() {
        return "Person{" +
            "name='" + name + '\'' +
            ", age=" + age +
            '}';
    }
}
