package vip.breakpoint.factory;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.breakpoint.annotion.WebLogging;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author :breakpoint/赵立刚
 */
public class LoggingJDKMethodInterceptor implements InvocationHandler {

    private static final Logger log = LoggerFactory.getLogger(LoggingJDKMethodInterceptor.class);

    private Map<String, Object> methodMap = new HashMap<String, Object>();

    private Object METHOD_VALUE = new Object();

    // 注解信息
    private WebLogging webLogging;

    private static SimpleDateFormat pdf;

    private EasyLoggingHandle easyLoggingHandle;

    // 代理的对象
    private Object target;

    public LoggingJDKMethodInterceptor(WebLogging webLogging, Object target, EasyLoggingHandle easyLoggingHandle) {
        this.webLogging = webLogging;
        this.target = target;
        for (String method : webLogging.methods()) {
            this.methodMap.put(method, METHOD_VALUE);
        }
        this.pdf = new SimpleDateFormat(webLogging.timePattern());
        this.easyLoggingHandle = easyLoggingHandle;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (null != methodMap.get(method.getName())) {
            log.info("接口请求数据:【{}】||方法:【{}】|| 时间:【{}】", JSONObject.toJSONString(args), method.getName(), pdf.format(new Date()));
            if (null != easyLoggingHandle) {
                easyLoggingHandle.invokeBefore(method.getName(), args);
            }
        }

        Object resVal = method.invoke(target, args);

        if (null != methodMap.get(method.getName())) {
            log.info("接口请求完成，进行返回结果：【{}】|| 完成时间:【{}】", JSONObject.toJSONString(resVal), pdf.format(new Date()));
            if (null != easyLoggingHandle) {
                easyLoggingHandle.invokeAfter(method.getName(), args, resVal);
            }
        }
        return resVal;
    }
}
