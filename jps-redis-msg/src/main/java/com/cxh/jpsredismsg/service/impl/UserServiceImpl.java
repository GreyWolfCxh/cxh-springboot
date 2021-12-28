package com.cxh.jpsredismsg.service.impl;





import com.cxh.jpsredismsg.entity.User;
import com.cxh.jpsredismsg.repository.UserRepository;
import com.cxh.jpsredismsg.service.IUserService;
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
        System.out.println("CacheManager type : " + cacheManager.getClass());
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
