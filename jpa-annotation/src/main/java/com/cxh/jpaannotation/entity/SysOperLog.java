package com.cxh.jpaannotation.entity;



import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * 操作日志记录表 oper_log
 */
@Entity
@Data
@NoArgsConstructor
public class SysOperLog {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;/** 日志主键 */
	private String operId;

	/** 操作模块 */
	private String title;

	/** 操作类型 */
	private Integer operType;

	/** 请求方法 */
	private String method;

	/** 请求方式 */
	private String requestMethod;

	/** 操作人员 */
	private String operName;


	/** 请求url */
	private String operUrl;

	/** 操作地址 */
	private String operIp;

	/** 操作地点 */
	private String operLocation;

	/** 请求参数 */
	private String operParam;

	/** 返回参数 */
	private String jsonResult;

	/** 操作状态（0正常 1异常） */
	private Integer status;

	/** 错误消息 */
	private String errorMsg;

	/** 操作时间 */
	private Date operTime;


}
