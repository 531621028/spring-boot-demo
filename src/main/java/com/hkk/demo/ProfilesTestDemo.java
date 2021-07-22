package com.hkk.demo;

import org.springframework.core.env.Profiles;

/**
 * ProfilesTestDemo
 *
 * @author hukangkang
 * @since 2021/7/22
 */
public class ProfilesTestDemo {

    public static void main(String[] args) {
        String[] value = new String[]{"local,test"};
        // Profiles是一个函数式结果
        // Profiles.of -> ProfilesParser.parse -> parseExpression -> parseTokens
        // 每一个被parse之后返回的Profile是一个lambda匿名内部类，会调用传入的Predicate<String> 来做判断
        Profiles profiles = Profiles.of(value);
        profiles.matches(ProfilesTestDemo::isProfileActive);
    }

    protected static boolean isProfileActive(String profile) {
        System.out.println(profile);
        return profile != null;
    }


}
