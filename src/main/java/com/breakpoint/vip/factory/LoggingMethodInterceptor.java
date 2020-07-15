package com.breakpoint.vip.factory;

import com.alibaba.fastjson.JSONObject;
import com.breakpoint.vip.annotion.WebLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 真正执行的地方
 *
 * @author :breakpoint/赵立刚
 * @date : 2020/07/15
 */
public class LoggingMethodInterceptor implements InvocationHandler {

    private static final Logger log = LoggerFactory.getLogger(LoggingMethodInterceptor.class);

    private List<String> methods;

    // 注解信息
    private WebLogging webLogging;

    private static SimpleDateFormat pdf;

    // 代理的对象
    private Object target;


    public LoggingMethodInterceptor(WebLogging webLogging, Object target) {
        this.webLogging = webLogging;
        this.target = target;
        this.methods = Arrays.asList(webLogging.methods());
        this.pdf = new SimpleDateFormat(webLogging.timePattern());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (methods.contains(method.getName())) {
            log.info("接口请求数据:【{}】||方法:【{}】|| 时间:【{}】", JSONObject.toJSONString(args), method.getName(), pdf.format(new Date()));
        }
        Object resVal = method.invoke(target, args);
        if (methods.contains(method.getName())) {
            log.info("接口请求完成，进行返回结果：【{}】", JSONObject.toJSONString(resVal));
        }
        return resVal;
    }
}
