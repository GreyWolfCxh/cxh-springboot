package com.cxh.jparedis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class JpaRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaRedisApplication.class, args);
    }

}
