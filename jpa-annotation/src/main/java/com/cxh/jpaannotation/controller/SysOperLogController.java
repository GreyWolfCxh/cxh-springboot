package com.cxh.jpaannotation.controller;

import com.cxh.jpaannotation.annotation.OperLog;
import com.cxh.jpaannotation.entity.SysOperLog;
import com.cxh.jpaannotation.service.ISysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sysOperLog")
public class SysOperLogController {

    @Autowired
    private ISysOperLogService sysOperLogService;

    @GetMapping("/findAll")
    @OperLog(title = "查询所有操作日志数据")
    public List<SysOperLog> findAll(){
        return sysOperLogService.findAll();
    }
}
