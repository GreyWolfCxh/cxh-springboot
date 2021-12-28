package com.cxh.jpsredismsg.controller;

import com.cxh.jpsredismsg.constant.RedisMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
public class RedisController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;



    @GetMapping("/publish")
    public String publish(@RequestParam String message) {
        redisTemplate.convertAndSend(RedisMsg.CHANNEL, message);
        return "发送消息成功";
    }

}