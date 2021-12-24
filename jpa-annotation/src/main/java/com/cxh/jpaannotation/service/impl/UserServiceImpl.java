package com.cxh.jpaannotation.service.impl;


import com.cxh.jpaannotation.entity.User;
import com.cxh.jpaannotation.repository.UserRepository;
import com.cxh.jpaannotation.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public String add(User user) {
        userRepository.save(user);
        return "添加成功";
    }

    @Override
    public String edit(User user) {
        userRepository.save(user);
        return "修改成功";
    }

    @Override
    public String deleteById(Long id) {
        userRepository.deleteById(id);
        return "删除成功";
    }
}
