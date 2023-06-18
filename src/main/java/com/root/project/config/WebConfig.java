package com.root.project.config;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.ResourceBundleMessageSource;

import javax.sql.DataSource;

@Configuration
public class WebConfig {

@Bean
GoogleAuthenticator googleAuthenticator(){
    return new GoogleAuthenticator();
}
}
