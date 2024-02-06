//package com.jdbc.hacaton1.databaseConfig;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DatabaseConfig {
//    @Value("jdbc:postgresql://localhost:5432/Hackaton%231")
//    private String url;
//
//    @Value("postgres")
//    private String username;
//
//    @Value("postgres")
//    private String password;
//
//    @Bean
//    public DataSource dataSource(){
//        return DataSourceBuilder
//                .create()
//                .url(url)
//                .username(username)
//                .password(password)
//                .build();
//    }
//}
