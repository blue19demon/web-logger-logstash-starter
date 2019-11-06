package com.logstash.core.builders;

import com.logstash.core.provider.FileLogAppendeServiceManager;
import com.logstash.core.provider.LogAppenderService;

public class InFileServiceBuilder extends LoggerAppenderServiceBuilder {
	private String logFilePath;
	private String sysCode;
	public InFileServiceBuilder logFilePath(String logFilePath) {
		this.logFilePath = logFilePath;
		return this;
	}
	public InFileServiceBuilder sysCode(String sysCode) {
		this.sysCode = sysCode;
		return this;
	}
	
	public String getSysCode() {
		return sysCode;
	}
	public String getLogFilePath() {
		return logFilePath;
	}
	
	/**
     * 开始构建
     */
    @Override
    protected LogAppenderService performBuild() {
        return new FileLogAppendeServiceManager(logFilePath,sysCode);
    }
}
