package com.cxh.mongodb.repository;

import com.cxh.mongodb.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {

    User findByName(String name);

}