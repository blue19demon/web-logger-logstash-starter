package com.logstash.log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConsoleLogger implements Logger{
	private Class<?> clazz;
	private static final String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS秒"));
	public ConsoleLogger(Class<?> clazz) {
		this.clazz = clazz;
	}

	@Override
	public void info(String msg) {
		Thread thread = Thread.currentThread();
		System.out.println(time+" INFO "+thread.getId()+" --- ["+thread.getName()+"] "+clazz.getName()+"  :"+ msg);
	}

	@Override
	public void error(String msg, Exception e) {
		Thread thread = Thread.currentThread();
		System.out.println(time+" INFO "+thread.getId()+" --- ["+thread.getName()+"] "+clazz.getName()+"  :"+ msg);
		System.out.println(time+" INFO 2584 --- [p-nio-82-exec-1] "+clazz.getName()+"  :"+ getStackTrace(e));
	}

}
