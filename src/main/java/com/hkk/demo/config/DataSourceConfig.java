package com.hkk.demo.config;

import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author kang
 * @date 2022/8/5
 */
@Configuration
public class DataSourceConfig {

    // @Bean
    // @Primary
    //ConfigurationProperties(prefix="spring.datasource") 将配置文件中的值设置到返回值对象的属性上
    //DataSource 是接口但是其实现类HikariDataSource extends HikariConfig 有对应的属性，
    // 如果是其他的实现类没有对应属性的则不能这样使用，要先绑定到一个properties类上，再实例化对应的DataSource
    // @ConfigurationProperties(prefix = "spring.datasource")
    // public DataSource primaryDataSource() {
    //     return DataSourceBuilder.create().build();
    // }
}
