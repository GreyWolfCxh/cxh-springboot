package com.cxh.jpa.controller;


import com.cxh.jpa.entity.User;
import com.cxh.jpa.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/findAll")
    public List<User> findAll(){
        return userService.findAll();
    }

    @GetMapping("/findByName")
    public User findByName(String name){
        return userService.findByName(name);
    }

    @PostMapping("/add")
    public String add(User user){
        return userService.add(user);
    }

    @PutMapping("/edit")
    public String edit(User user){
        return userService.edit(user);
    }

    @DeleteMapping("/deleteById")
    public String deleteById(Long id){
        return userService.deleteById(id);
    }



}
