package com.cxh.jpaannotation.service.impl;

import com.cxh.jpaannotation.entity.SysOperLog;
import com.cxh.jpaannotation.repository.SysOperLogRepository;
import com.cxh.jpaannotation.service.ISysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SysOperLogServiceImpl implements ISysOperLogService {

    @Autowired
    private SysOperLogRepository sysOperLogRepository;

    @Override
    public void save(SysOperLog sysOperLog) {
        sysOperLogRepository.save(sysOperLog);
    }

    @Override
    public List<SysOperLog> findAll() {
        return sysOperLogRepository.findAll();
    }
}
