package com.logstash.core.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import com.logstash.bean.OperatorLog;
import com.logstash.core.configurers.LogerAppenderConfigurer;
import com.logstash.core.configurers.LogerAppernderServiceConfigurer;

@Component
public class LogAppendeServiceManager {
	@Autowired
	private LogerAppenderConfigurer configurer;
	@Autowired
	private AutowireCapableBeanFactory spring;
	private LogAppenderService detailsService;

	@Autowired
	protected void configure(LogerAppernderServiceConfigurer logerAppernderServiceConfigurer) {
		try {
			configurer.configure(logerAppernderServiceConfigurer);
			detailsService = logerAppernderServiceConfigurer.getBuilder().build();
			spring.autowireBean(detailsService);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void append(OperatorLog operatorLog) {
		try {
			detailsService.append(operatorLog);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
