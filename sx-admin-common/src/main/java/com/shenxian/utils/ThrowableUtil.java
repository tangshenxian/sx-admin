package com.shenxian.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常工具类
 * @author: shenxian
 * @date: 2022/7/11 11:23
 */
public class ThrowableUtil {

    /**
     * 获取堆栈信息
     * @param throwable
     * @return
     */
    public static String getStackTrace(Throwable throwable){
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }

}
