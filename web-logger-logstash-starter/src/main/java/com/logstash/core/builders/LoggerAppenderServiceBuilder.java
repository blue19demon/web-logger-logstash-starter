package com.logstash.core.builders;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.logstash.core.provider.LogAppenderService;

@Component
public class LoggerAppenderServiceBuilder  implements LoggerBuilder<LogAppenderService> {

	public static final InFileServiceBuilder inFile() {
		return new InFileServiceBuilder();
	}

	public static final JdbcServiceBuilder jdbc(JdbcTemplate jdbcTemplate) {
		 return new JdbcServiceBuilder(jdbcTemplate);
	}

	@Override
	public LogAppenderService build() throws Exception {
		 return performBuild();
	}
	
	 /**
     * 开始构建
     * @return 商户列表服务
     */
    protected  LogAppenderService performBuild(){
        throw new UnsupportedOperationException("无法构建(需要使用inFile()或jdbc())");
    }

	public static final JdbcServiceBuilder jdbc(DataSource source) {
		 return new JdbcServiceBuilder(source);
	}

}
