package com.logstash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebLoggerLogstashSampleApplication {
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(WebLoggerLogstashSampleApplication.class);
		springApplication.run(args);
	}
}
