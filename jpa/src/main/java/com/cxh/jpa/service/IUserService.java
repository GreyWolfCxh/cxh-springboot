package com.cxh.jpa.service;


import com.cxh.jpa.entity.User;

import java.util.List;

public interface IUserService {
    List<User> findAll();

    User findByName(String name);

    String add(User user);

    String edit(User user);

    String deleteById(Long id);
}
