package com.logstash.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.logstash.bean.OperatorLog;
import com.logstash.core.provider.LogAppendeServiceManager;

@Component
public class OptLogService {

	@Autowired
	private LogAppendeServiceManager manager;
	
	public void saveLog(OperatorLog optLog) {
		manager.append(optLog);
	}

}
