package com.cxh.async_task;

import com.cxh.async_task.task.AsyncTasks;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@EnableAsync
@SpringBootApplication
public class AsyncTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsyncTaskApplication.class, args);
    }
}
