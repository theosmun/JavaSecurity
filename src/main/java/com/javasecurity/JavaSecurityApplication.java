package com.javasecurity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.javasecurity.api.app.mapper")
public class JavaSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaSecurityApplication.class, args);
    }

}
