package com.kodark.news.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = { "com.kodark.news.service", "com.kodark.news.dao", "com.kodark.news.utils" })
@Import({ MailConfig.class, DBConfig.class, MyBatisConfig.class, SecurityConfig.class })
@PropertySource("classpath:secretKey.properties")
public class ApplicationContextConfig {

}