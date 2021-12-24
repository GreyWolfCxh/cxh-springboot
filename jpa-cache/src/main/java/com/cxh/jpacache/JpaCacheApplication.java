package com.cxh.jpacache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@EnableCaching
@SpringBootApplication
public class JpaCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaCacheApplication.class, args);
    }


}

