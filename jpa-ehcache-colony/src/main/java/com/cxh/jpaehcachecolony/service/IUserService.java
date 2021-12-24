package com.cxh.jpaehcachecolony.service;





import com.cxh.jpaehcachecolony.entity.User;

import java.util.List;

public interface IUserService {
    List<User> findAll();

    User findByName(String name);

    String add(User user);

    String edit(User user);

    String deleteById(Long id);
}
