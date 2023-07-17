package com.hkk.demo.ioc;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author kang
 * @date 2023/7/4
 */
@Component
public class StaticMethodDemo {


    @Bean
    static BeanFactoryPostProcessor staticMethod() {
        return new MyBeanFactoryPostProcessor();
    }

    static class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

        MyBeanFactoryPostProcessor() {
            System.out.println("MyBeanFactoryPostProcessor init...");
        }

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        }
    }

}
