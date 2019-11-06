package com.logstash.aspect;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.logstash.annos.API;
import com.logstash.annos.LogstashModule;
import com.logstash.bean.OperatorLog;
import com.logstash.core.OptLogService;
import com.logstash.log.Logger;
import com.logstash.log.LoggerFactoy;
import com.logstash.utils.LogAopUtil;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
public class WebRequestLogAspect {

	private ThreadLocal<OperatorLog> tlocal = new ThreadLocal<OperatorLog>();

	private static Logger log = LoggerFactoy.log(WebRequestLogAspect.class);
	@Autowired
	private OptLogService optLogService;

	@Pointcut("within(@com.logstash.annos.LogstashModule *)")
	public void webRequestLog() {
	}

	@Pointcut("within(@com.logstash.annos.LogstashModule *)")
	public void operExceptionLogPoinCut() {
	}

	@Before("webRequestLog()")
	public void doBefore(JoinPoint joinPoint) {
		try {

			long beginTime = System.currentTimeMillis();

			// 接收到请求，记录请求内容
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
			String beanName = joinPoint.getSignature().getDeclaringTypeName();
			String methodName = joinPoint.getSignature().getName();
			String uri = request.getRequestURL().toString();
			String remoteAddr = getIpAddr(request);
			String sessionId = request.getSession().getId();
			String user = (String) request.getSession().getAttribute("user");
			String requestMethod = request.getMethod();
			String params = "";
			Object[] args = joinPoint.getArgs();
			// 拦截的实体类，就是当前正在执行的
			Object target = joinPoint.getTarget();
			// 拦截的放参数类型
			Signature sig = joinPoint.getSignature();
			MethodSignature msig = null;
			if (!(sig instanceof MethodSignature)) {
				throw new IllegalArgumentException("该注解只能用于方法");
			}
			msig = (MethodSignature) sig;
			Class<?>[] parameterTypes = msig.getMethod().getParameterTypes();

			Method methodClass = null;
			try {
				methodClass = target.getClass().getMethod(methodName, parameterTypes);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			OperatorLog optLog = new OperatorLog();
			if (null != methodClass) {
				// 判断是否包含自定义的注解，说明一下这里的SystemLog就是我自己自定义的注解
				if (target.getClass().isAnnotationPresent(LogstashModule.class)) {
					LogstashModule module = target.getClass().getAnnotation(LogstashModule.class);
					optLog.setModule(module.value());
				}
				if (methodClass.isAnnotationPresent(API.class)) {
					API api = methodClass.getAnnotation(API.class);
					optLog.setApi(api.value());
				}
			} else { // 不需要拦截直接执行
				log.info("不需要拦截直接执行");
			}
			StringBuffer sb = LogAopUtil.getNameAndArgs(this.getClass(), target.getClass().getName(), methodName, args);
			params = sb != null ? sb.toString() : "";
			optLog.setBeanName(beanName);
			optLog.setCurUser(user);
			optLog.setRequestMethod(requestMethod);
			optLog.setParams(params != null ? params.toString() : "");
			optLog.setRemoteAddr(remoteAddr);
			optLog.setSessionId(sessionId);
			optLog.setUri(uri);
			optLog.setExcuteTime(beginTime);

			tlocal.set(optLog);

		} catch (Exception e) {
			log.error("***操作请求日志记录失败doBefore()***", e);
		}
	}

	@AfterReturning(returning = "result", pointcut = "webRequestLog()")
	public void doAfterReturning(Object result) {
		try {
			// 处理完请求，返回内容
			OperatorLog optLog = tlocal.get();
			if (optLog != null) {
				handleLogs(result, optLog);
			}
		} catch (Exception e) {
			log.error("***操作请求日志记录失败doAfterReturning()***", e);
		}
	}

	private void handleLogs(Object result, OperatorLog optLog) {
		optLog.setResult(result != null ? result.toString() : "");
		long beginTime = optLog.getExcuteTime();
		long requestTime = (System.currentTimeMillis() - beginTime);
		optLog.setExcuteTime(requestTime);
		// 打印请求内容
		log.info("===============请求内容===============");
		log.info("请求地址:" + optLog.getUri());
		log.info("请求方式:" + optLog.getRequestMethod());
		log.info("请求参数:" + optLog.getParams());
		log.info("响应结果 : " + result);
		log.info("请求耗时：" + optLog.getExcuteTime() + "ms");
		log.info("===============请求内容===============");
		log.info(JSONObject.toJSONString(optLog, true));
		optLogService.saveLog(optLog);
		log.info("保存日志结果:success");

	}

	@AfterThrowing(pointcut = "operExceptionLogPoinCut()", throwing = "e")
	public void saveExceptionLog(JoinPoint joinPoint, Throwable e) {
		OperatorLog optLog = tlocal.get();
		if (optLog != null) {
			optLog.setExcName(e.getClass().getName()); // 异常名称
			optLog.setExcMessage(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace())); // 异常信息
			handleLogs(null, optLog);
		}
	}

	public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
		StringBuffer strbuff = new StringBuffer();
		for (StackTraceElement stet : elements) {
			strbuff.append(stet + "\n");
		}
		String message = exceptionName + ":" + exceptionMessage + "\n\t" + strbuff.toString();
		return message.substring(0, 500).concat(".....");
	}

	/**
	 * 获取登录用户远程主机ip地址
	 * 
	 * @param request
	 * @return
	 */
	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}