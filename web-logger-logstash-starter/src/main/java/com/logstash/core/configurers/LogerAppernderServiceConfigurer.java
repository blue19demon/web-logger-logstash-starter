package com.logstash.core.configurers;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.logstash.core.builders.InFileServiceBuilder;
import com.logstash.core.builders.JdbcServiceBuilder;
import com.logstash.core.builders.LoggerAppenderServiceBuilder;
import com.logstash.core.provider.LoggerAppenderConfigurerAdapter;

@Component
public class LogerAppernderServiceConfigurer implements LoggerAppenderConfigurerAdapter<LoggerAppenderServiceBuilder> {
	private LoggerAppenderServiceBuilder builder;

	public void setBuilder(LoggerAppenderServiceBuilder builder) {
		this.builder = builder;
	}

	public InFileServiceBuilder inFile() {
		InFileServiceBuilder builder = LoggerAppenderServiceBuilder.inFile();
		initBuilder(builder);
		return builder;
	}

	public LoggerAppenderServiceBuilder initBuilder(LoggerAppenderServiceBuilder builder) {
		setBuilder(builder);
		return builder;
	}

	public JdbcServiceBuilder jdbc(JdbcTemplate jdbcTemplate) {
		JdbcServiceBuilder builder = LoggerAppenderServiceBuilder.jdbc(jdbcTemplate);
		initBuilder(builder);
		return builder;
	}
	public JdbcServiceBuilder jdbc(DataSource source) {
		JdbcServiceBuilder builder = LoggerAppenderServiceBuilder.jdbc(source);
        initBuilder(builder);
		return builder;
	}

	/**
	 * 外部调用者使用，链式的做法
	 *
	 * @return 返回对应外部调用者
	 */
	@Override
	public LoggerAppenderServiceBuilder and() {
		return getBuilder();
	}

	/**
	 * 获取构建器
	 *
	 * @return 构建器
	 */
	@Override
	public LoggerAppenderServiceBuilder getBuilder() {
		return builder;
	}
}
