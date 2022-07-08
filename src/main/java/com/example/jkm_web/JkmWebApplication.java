package com.example.jkm_web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.example.jkm_web.dao"})
public class JkmWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(JkmWebApplication.class, args);
    }

}
