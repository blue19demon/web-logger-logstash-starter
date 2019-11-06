package com.logstash.log;

import java.io.PrintWriter;
import java.io.StringWriter;

public interface Logger {

	default public String getStackTrace(Throwable throwable)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try
        {
            throwable.printStackTrace(pw);
            return sw.toString();
        } finally
        {
            pw.close();
        }
    }
	void info(String string);

	void error(String string, Exception e);

}
