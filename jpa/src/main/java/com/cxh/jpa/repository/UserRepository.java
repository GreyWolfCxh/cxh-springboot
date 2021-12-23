package com.cxh.jpa.repository;


import com.cxh.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);


    @Query("from User u where u.name=:name")
    User findUser(@Param("name") String name);

}
