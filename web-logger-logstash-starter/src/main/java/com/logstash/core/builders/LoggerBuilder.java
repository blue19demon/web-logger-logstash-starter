package com.logstash.core.builders;

public interface LoggerBuilder<O> {


    /**
     * 构建对象并返回它或null。
     *
     * @return 如果实现允许，则要构建的对象或null。
     * @throws Exception 如果在构建对象时发生错误
     */
    O build() throws Exception;
}
