package com.logstash.core.provider;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import com.logstash.bean.OperatorLog;
public class FileLogAppendeServiceManager implements LogAppenderService{
	private String logFilePath;
	private String sysCode;
	

	public FileLogAppendeServiceManager(String logFilePath, String sysCode) {
		super();
		this.logFilePath = logFilePath;
		this.sysCode = sysCode;
	}


	@Override
	public void append(OperatorLog operatorLog) throws RuntimeException{
		FileWriter fileWriter = null;
		try {
			operatorLog.setSystem(sysCode);
			File logFile =new File(logFilePath);
			fileWriter = new FileWriter(logFile,true);//创建文本文件
			fileWriter.write(JSONObject.toJSONString(operatorLog, true)+"\r\n");//写入 \r\n换行
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
