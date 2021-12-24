package com.cxh.jpacache.service.impl;


import com.cxh.jpacache.entity.User;
import com.cxh.jpacache.repository.UserRepository;
import com.cxh.jpacache.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CacheManager cacheManager;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByName(String name) {
        User u1 = userRepository.findByName(name);
        System.out.println("第一次查询：" + u1.getAge());

        User u2 = userRepository.findByName(name);
        System.out.println("第二次查询：" + u2.getAge());
        return u2;
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
