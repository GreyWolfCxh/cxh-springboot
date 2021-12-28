package com.cxh.mongodb.service;







import com.cxh.mongodb.entity.User;

import java.util.List;

public interface IUserService {
    List<User> findAll();

    User findByName(String name);

    String add(User user);

    String edit(User user);

    String deleteById(Long id);
}
