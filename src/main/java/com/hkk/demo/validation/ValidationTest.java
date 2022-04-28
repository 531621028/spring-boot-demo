package com.hkk.demo.validation;

import com.hkk.demo.utils.ValidationUtils;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.validation.ConstraintViolation;

/**
 * @author kang
 * @date 2022/4/21
 */
public class ValidationTest {

    public static void main(String[] args) {
        Person person = new Person();
        //person.setName("fsx");
        person.setAge(-1);
        // email校验：虽然是List都可以校验哦
        person.setEmails(Arrays.asList("fsx@gmail.com", "baidu@baidu.com", "aaa.com"));
        //person.setStart(new Date()); //start 需要是一个将来的时间: Sun Jul 21 10:45:03 CST 2019
        //person.setStart(new Date(System.currentTimeMillis() + 10000)); //校验通过

        // 对person进行校验然后拿到结果（显然使用时默认的校验器）   会保留下校验失败的消息
        Set<ConstraintViolation<Person>> result = ValidationUtils.validate(person);
        // 对结果进行遍历输出
        result.stream()
            .map(v -> v.getPropertyPath() + " " + v.getMessage() + ": " + v.getInvalidValue())
            .forEach(System.out::println);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        System.out.println(executorService);
    }
}
