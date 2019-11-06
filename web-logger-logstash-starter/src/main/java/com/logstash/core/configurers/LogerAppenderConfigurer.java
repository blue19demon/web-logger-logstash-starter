package com.logstash.core.configurers;

public interface LogerAppenderConfigurer {
	 /**
     * appender 即日志输出路径配置
     * @throws Exception 异常
     */
    void configure(LogerAppernderServiceConfigurer configurer) throws Exception;
}
