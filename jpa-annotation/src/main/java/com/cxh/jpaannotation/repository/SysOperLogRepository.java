package com.cxh.jpaannotation.repository;

import com.cxh.jpaannotation.entity.SysOperLog;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SysOperLogRepository extends JpaRepository<SysOperLog, Long> {
}
