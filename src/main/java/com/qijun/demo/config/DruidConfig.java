package com.qijun.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @ClassName DruidConfig
 * @Description 配置数据库连接池 开启事务支持
 * @Author Qijun
 * @Date 11/23/18 3:17 PM
 * @Version 1.0
 */
@Configuration
@EnableTransactionManagement
@PropertySource(value = "classpath:config/db.properties")
public class DruidConfig {

    /**
     * 配置数据源 Druid DataSource
     * @return
     */
    @Bean(destroyMethod = "close", initMethod = "init")
    @ConfigurationProperties(prefix="spring.datasource")
    public DruidDataSource druidDataSource() {
        DruidDataSource source = new DruidDataSource();
        return source;
    }

    //HikariCP性能最强
}

