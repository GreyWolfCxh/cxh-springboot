package com.cxh.jpaannotation.service;

import com.cxh.jpaannotation.entity.SysOperLog;
import com.cxh.jpaannotation.entity.User;

import java.util.List;

/**
 * @author ChenXihua
 * @description
 * @date 2021/12/24 15:19
 */
public interface ISysOperLogService {
    /**
     * @description 添加日志
     * @param  sysOperLog
     * @return  void
     **/
    void save(SysOperLog sysOperLog);

    List<SysOperLog> findAll();
}
