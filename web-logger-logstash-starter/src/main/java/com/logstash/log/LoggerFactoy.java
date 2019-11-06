package com.logstash.log;

public class LoggerFactoy {

	public static Logger log(Class<?> clazz) {
		return new ConsoleLogger(clazz);
	}

}
