package com.example.jkm_web;

import io.swagger.models.auth.In;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import springfox.documentation.oas.annotations.EnableOpenApi;



@EnableOpenApi
@SpringBootApplication
@MapperScan(basePackages = {"com.example.jkm_web.dao"})
public class JkmWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(JkmWebApplication.class, args);
    }

}
