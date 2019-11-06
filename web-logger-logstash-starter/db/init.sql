DROP TABLE
IF
	EXISTS `operator_Log`;
CREATE TABLE `operator_Log` (
`id` BIGINT ( 20 )  NOT NULL AUTO_INCREMENT,
`excute_time` LONG DEFAULT NULL COMMENT '接口執行耗時（毫秒）',
`remote_addr` VARCHAR ( 1024 ) DEFAULT NULL COMMENT '请求IP',
`request_method` VARCHAR ( 1024 ) DEFAULT NULL COMMENT 'HTTP方法',
`system` VARCHAR ( 1024 ) DEFAULT NULL COMMENT '所属系统',
`module` VARCHAR ( 1024 ) DEFAULT NULL COMMENT '請求模塊',
`api` VARCHAR ( 1024 ) DEFAULT NULL COMMENT '接口功能描述',
`uri` VARCHAR ( 1024 ) DEFAULT NULL COMMENT '请求URL',
`params` VARCHAR ( 1024 ) DEFAULT NULL COMMENT '请求参数',
`session_id` VARCHAR ( 1024 ) DEFAULT NULL COMMENT '会话ID',
`cur_user` VARCHAR ( 255 ) DEFAULT NULL COMMENT '当前用户(預留字段)',
`bean_name` VARCHAR ( 255 ) DEFAULT NULL COMMENT '类名',
`result` VARCHAR ( 1024 ) DEFAULT NULL COMMENT '请求结果',
`time` VARCHAR ( 1024 ) DEFAULT NULL COMMENT '请求时间',
`exc_name` VARCHAR ( 1024 ) DEFAULT NULL COMMENT '异常名称',
`exc_message` VARCHAR ( 1024 ) DEFAULT NULL COMMENT '异常内容',
`created` datetime DEFAULT NULL COMMENT '創建時間',
PRIMARY KEY ( `id` ) 
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8;