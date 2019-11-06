package com.logstash;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.logstash.core.configurers.LogerAppenderConfigurer;
import com.logstash.core.configurers.LogerAppernderServiceConfigurer;

@Configuration
public class LogerAppenderConfig implements LogerAppenderConfigurer {
	/*
	 * @Autowired private JdbcTemplate jdbcTemplate;
	 * 
	 * @Autowired private DataSource dataSource;
	 */
	public void configure(LogerAppernderServiceConfigurer configurer) throws Exception {
		// 数据库文件存放 /db目录下
		//configurer.jdbc(jdbcTemplate).sysCode("demo-system");
		// 数据库文件存放 /db目录下
		// configurer.jdbc(dataSource).sysCode("demo-system");
		/// 文件模式
		 configurer.inFile().sysCode("demo-system").logFilePath("demo-system.log");

	}
}
