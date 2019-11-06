# common-logs-starter-reconsitution

#### 介绍
一个基于AOP的通用日志解决方案starter
https://gitee.com/hj2017hhh/common-logs-starter-solution/tree/master
的重构版本，使用Builder设计模式，使用起来更加轻松，不在配置springboot上下文文件
#### 软件架构
使用了Builder设计模式+javassist+AOP

#### 安装教程

详见使用说明

#### 使用说明

#### 1.下载starter
#### 2.再使用方pom.xml加入如下配置
      <dependency>
	    <groupId>com.logstash</groupId>
	    <artifactId>web-logger-logstash-starter</artifactId>
	    <version>0.0.2-SNAPSHOT</version>
     </dependency>	
#### 3.加入配置
        @Configuration
        public class LogerAppenderConfig implements LogerAppenderConfigurer {
        	@Autowired
        	private JdbcTemplate jdbcTemplate;
        	@Autowired
        	private DataSource dataSource;
        
        	@Override
        	public void configure(LogerAppernderServiceConfigurer configurer) throws Exception {
        		// 数据库文件存放 /db目录下
        		// configurer.jdbc(jdbcTemplate).sysCode("demo-system");
        		// 数据库文件存放 /db目录下
        		configurer.jdbc(dataSource).sysCode("demo-system");
        		/// 文件模式
        		// configurer.inFile().sysCode("demo-system").logFilePath("demo-system.log");
        
        	}
        }
#### 4.附加配置
 #### 4.1 如果是logType为JDBC,请下载文件https://gitee.com/hj2017hhh/common-logs-starter-reconsitution/blob/master/web-logger-logstash-starter/db/init.sql
 #### 4.2 到你的数据库中执行，并加入配置：
	 spring:
	  datasource:
	    driver-class-name: com.mysql.jdbc.Driver
	    url: jdbc:mysql://127.0.0.1:3308/test?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=false
	    username: root
	    password: 123456

 #### 5.使用说明，请在Controller层使用，其它层的，比如service层，会导致记录的日志不准确，待解决！
	package com.demo.controller;
	@RestController
	@LogstashModule("用户管理")//加入该注解就会记录日志
	public class UserController {

		@Autowired
		private UserService userService;

		/**
		 * 登陆消息验证
		 */
		@RequestMapping(value = "/toLogin")
		@API("用户登陆")//接口功能介绍
		public JSONOperation<?> toLogin(UserAuth userAuth, HttpServletRequest request, HttpServletRequest response) {
			//System.out.println(1/0);//测试异常
			JSONObject loginJson = userService.userLogin(userAuth,true);
			// 表示登陆成功
			if (loginJson.get("key").equals("success")) {
				return JSONOperation.success("登陆成功!", userAuth);
			}
			return JSONOperation.fail(loginJson.getString("msg"));
		}

		/**
		 * 用户列表
		 */
		@RequestMapping(value = "/list")
		@API("用户列表")
		public JSONOperation<?> list(String name, HttpServletRequest request, HttpServletRequest response) {
			List<UserAuth> list = userService.list(name);
			return JSONOperation.success(list);
		}
	}

#### 6.经过测试，不加注解的Controller就不会开启日志，对业务没有侵入性


#### 7.异常情况下也会记录到日志

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 码云特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5.  码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
