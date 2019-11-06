package com.logstash.log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConsoleLogger implements Logger{
	private Class<?> clazz;
	private static final String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
	public ConsoleLogger(Class<?> clazz) {
		this.clazz = clazz;
	}

	@Override
	public void info(String msg) {
		Thread thread = Thread.currentThread();
		String pid= format(thread.getId(),4);
		System.out.println(time+"  INFO "+pid+" --- ["+thread.getName()+"] "+clazz.getName()+"  :"+ msg);
	}

	private String format(long id, int size) {
		//不超过size
		if(id>0&&id<Math.pow(10, size)-1) {
			return String.valueOf(judge(id,size));
		}
		//超过size
		return String.valueOf((int)(id%Math.pow(10, size)));
	}
	private String judge(long id, int size) {
		StringBuffer newStr=new StringBuffer();
		int n=0;
		long m=id;
		while(m>0) {
			m=m/(10);
			n++;
		}
		for (int i = 1; i <=size-n; i++) {
			newStr.append(" ");
		}
		newStr.append(String.valueOf(id));
		return newStr.toString();
	}


	@Override
	public void error(String msg, Throwable e) {
		Thread thread = Thread.currentThread();
		String pid= format(thread.getId(),4);
		System.err.println(time+"  ERROR "+pid+" --- ["+thread.getName()+"] "+clazz.getName()+"  :"+ msg);
		System.err.println(time+"  ERROR "+pid+" --- [p-nio-82-exec-1] "+clazz.getName()+"  :"+ getStackTrace(e));
	}

}
