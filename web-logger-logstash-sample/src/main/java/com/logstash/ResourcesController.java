package com.logstash;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logstash.annos.API;
import com.logstash.annos.LogstashModule;

@RestController
//不加这句，表示不收集日志
@LogstashModule("资源管理")
public class ResourcesController {

	@RequestMapping(value = "/resources")
	@API("资源记录")
	public JSONOperation<?> resources() {
		System.out.println(1/0);
		return JSONOperation.success("this is resources");
	}
	
	@RequestMapping(value = "/api1")
	@API("测试简单多参数")
	public JSONOperation<?> api1(String name,int id) {
		return JSONOperation.success(name+","+id);
	}
	
	@RequestMapping(value = "/api2/{name}/{id}")
	@API("测试简单@Path多参数")
	public JSONOperation<?> api2(@PathVariable("name")String name,@PathVariable("id")int id) {
		return JSONOperation.success(name+",api2,"+id);
	}
}
