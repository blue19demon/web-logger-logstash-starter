package com.logstash.core.provider;

import com.logstash.bean.OperatorLog;

public interface LogAppenderService {

	public void append(OperatorLog operatorLog);
}
