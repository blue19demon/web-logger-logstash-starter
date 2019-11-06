package com.logstash.core.provider;

import static com.logstash.utils.SqlTools.SEPARATED;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.logstash.bean.OperatorLog;
import com.logstash.utils.SqlTools;

public class JDBCLogAppendeServiceManager implements LogAppenderService {

	 private static final String TABLE = "operator_log";
	    private static final List<String> FIELDS = Arrays.asList(
	    		"excute_time "
				, "remote_addr"
				, "request_method"
				, "system"
				, "module"
				, "api"
				, "uri"
				, "params"
				, "session_id"
				, "cur_user"
				, "bean_name "
				, "result"
				, "time"
				, "exc_name"
				, "exc_message "
				, "created");
	    private static final String SELECT_FIELDS = SqlTools.join(FIELDS, SEPARATED);
	    private static final String DEFAULT_INSERT_SQL = "insert into " + TABLE + " ("  + SELECT_FIELDS + ") values (" + SqlTools.forQuestionMarkSQL(FIELDS.size()) + ")";

	    private JdbcTemplate jdbcTemplate;
	    private String sysCode;
	    private String insertSql = DEFAULT_INSERT_SQL;
	    
	
	
	public JDBCLogAppendeServiceManager(JdbcTemplate jdbcTemplate, String sysCode) {
			super();
			this.jdbcTemplate = jdbcTemplate;
			this.sysCode = sysCode;
	}

	public JDBCLogAppendeServiceManager(DataSource dataSource) {
		super();
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void append(OperatorLog operatorLog) throws RuntimeException{
		Object[] args = new Object[]{operatorLog.getExcuteTime(),
				operatorLog.getRemoteAddr(),
				operatorLog.getRequestMethod(),
				sysCode,
				operatorLog.getModule(),
				operatorLog.getApi(),
				operatorLog.getUri(),
				operatorLog.getParams(),
				operatorLog.getSessionId(),
				operatorLog.getCurUser(),
				operatorLog.getBeanName(),
				operatorLog.getResult(),
				operatorLog.getTime(),
				operatorLog.getExcName(),
				operatorLog.getExcMessage(),
				new Date()};
        jdbcTemplate.update(insertSql, args);
	}

}
