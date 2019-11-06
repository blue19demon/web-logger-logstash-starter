package com.logstash.core.builders;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.logstash.core.provider.JDBCLogAppendeServiceManager;
import com.logstash.core.provider.LogAppenderService;

public class JdbcServiceBuilder extends LoggerAppenderServiceBuilder{

	private JdbcTemplate jdbcTemplate;
	private String sysCode;
	public JdbcServiceBuilder sysCode(String sysCode) {
		this.sysCode = sysCode;
		return this;
	}
	public String getSysCode() {
		return sysCode;
	}
	public JdbcServiceBuilder(DataSource source) {
        jdbcTemplate = new JdbcTemplate(source);
    }

	public JdbcServiceBuilder(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		   if (null != this.jdbcTemplate){
	            return;
	        }
		this.jdbcTemplate = jdbcTemplate;
	}
	/**
     * 开始构建
     *
     * @return 商户列表服务·
     */
    @Override
    protected LogAppenderService performBuild() {
        return new JDBCLogAppendeServiceManager(jdbcTemplate,sysCode);
    }
}
