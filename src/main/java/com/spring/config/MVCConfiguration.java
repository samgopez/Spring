package com.spring.config;

import com.spring.service.UserService;
import com.spring.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;

/**
 * Created by Sam on 19/11/2016.
 */

@Configuration
@ComponentScan(basePackages = "com.spring")
@EnableWebMvc
public class MVCConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public ViewResolver getViewResolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/database?autoReconnect=true&useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("password");

        return dataSource;
    }

    @Bean
    public UserService getContactDAO() {
        return new UserServiceImpl(getDataSource());
    }
}
