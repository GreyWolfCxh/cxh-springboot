package com.cxh.jpaehcachecolony.service.impl;




import com.cxh.jpaehcachecolony.entity.User;
import com.cxh.jpaehcachecolony.repository.UserRepository;
import com.cxh.jpaehcachecolony.service.IUserService;
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
        return u1;
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
