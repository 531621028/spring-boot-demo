package com.hkk.demo.utils;

import com.hkk.demo.domain.Person;
import java.util.Map;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;

/**
 * DataBinderDemo
 *
 * @author hukangkang
 * @since 2021/6/30
 */
public class DataBinderDemo {

    public static void main(String[] args) throws BindException {
        Person person = new Person();
        DataBinder binder = new DataBinder(person, "person");
        MutablePropertyValues pvs = new MutablePropertyValues();
        pvs.add("name", "fsx");
        pvs.add("age", 18);

        binder.bind(pvs);
        Map<?, ?> close = binder.close();

        System.out.println(person);
        System.out.println(close);
    }
}
