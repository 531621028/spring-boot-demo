package com.hkk.demo.config;

import com.hkk.demo.service.CycleServiceTest;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * factoryBean 循环依赖
 *
 * @author hukangkang
 * @since 2021/9/27
 */
@Component("cycleConfig")
public class CycleConfig {

    @Autowired
    private CycleServiceTest cycleServiceTest;

    @Bean
    public BeanDefinitionRegistryPostProcessor testPostProcessor() {
        return new BeanDefinitionRegistryPostProcessor() {
            @Override
            public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
                System.out.println("postProcessBeanDefinitionRegistry");
            }

            @Override
            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
                System.out.println("postProcessBeanFactory");
            }
        };
    }

}
