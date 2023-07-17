package com.hkk.demo.jackson;

import com.fasterxml.jackson.annotation.JsonGetter;

public class MyBean {

    public MyBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int id;
    private String name;

    // @JsonGetter("name")
    public String getTheName() {
        return name;
    }
}