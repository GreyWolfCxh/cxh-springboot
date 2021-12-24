package com.cxh.jpaannotation.controller;


import com.cxh.jpaannotation.annotation.OperLog;
import com.cxh.jpaannotation.constant.OperType;
import com.cxh.jpaannotation.entity.User;
import com.cxh.jpaannotation.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/findAll")
    @OperLog(title = "查询所有用户数据")
    public List<User> findAll(){
        return userService.findAll();
    }

    @GetMapping("/findByName")
    @OperLog(title = "查询根据名称的用户数据")
    public User findByName(String name){
        return userService.findByName(name);
    }

    @PostMapping("/add")
    @OperLog(title = "添加用户数据", operType = OperType.INSERT)
    public String add(User user){
        return userService.add(user);
    }

    @PutMapping("/edit")
    @OperLog(title = "修改用户数据", operType = OperType.UPDATE)
    public String edit(User user){
        return userService.edit(user);
    }

    @DeleteMapping("/deleteById")
    @OperLog(title = "删除用户数据", operType = OperType.DELETE)
    public String deleteById(Long id){
        return userService.deleteById(id);
    }



}
