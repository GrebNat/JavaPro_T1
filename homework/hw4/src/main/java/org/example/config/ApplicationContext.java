package org.example.config;

import org.example.ioc.UserDao;
import org.example.ioc.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContext {
/*
    @Bean
    public UserService userService(UserDao userDao) {
        return new UserService(userDao);
    }

    @Bean
    public UserDao userDao(DataSourceConfig dataSourceConfig) {
        return new UserDao(dataSourceConfig);
    }

   @Bean
    public DataSourceConfig dataSourceConfig() {
        return new DataSourceConfig();
    }*/
}
