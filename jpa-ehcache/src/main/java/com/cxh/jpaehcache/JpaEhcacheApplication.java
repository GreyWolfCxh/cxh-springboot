package com.cxh.jpaehcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class JpaEhcacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaEhcacheApplication.class, args);
    }

}
