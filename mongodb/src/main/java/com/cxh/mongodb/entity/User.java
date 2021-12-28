package com.cxh.mongodb.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class User {

    @Id
    private Long id;

    private String name;
    private Integer age;

}