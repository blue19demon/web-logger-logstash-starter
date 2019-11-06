package com.logstash.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OperatorLog implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	/**
	 * 类名
	 */
	private String beanName;
	/**
	 * 当前用户
	 */
	private String curUser;
	/**
	 * HTTP方法
	 */
	private String requestMethod;
	/**
	 * 请求参数
	 */
	private String params;
	/**
	 * 请求IP
	 */
	private String remoteAddr;
	/**
	 * 会话ID
	 */
	private String sessionId;
	/**
	 * 请求URL
	 */
	private String uri;
	/**
	 * 接口執行耗時（毫秒）
	 */
	private long excuteTime;
	/**
	 * 请求结果
	 */
	private String result;
	/**
	 * 請求模塊
	 */
	private String module;
	/**
	 * 接口功能描述
	 */
	private String api;
	/**
	 * 所属系统
	 */
	private String system;
	/**
	 * 请求时间
	 */
	private String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss.SSS秒"));
	/**
	 * 异常名称
	 */
	private String excName;
	/**
	 * 异常内容
	 */
	private String excMessage;
}
