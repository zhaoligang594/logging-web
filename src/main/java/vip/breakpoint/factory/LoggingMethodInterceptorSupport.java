package vip.breakpoint.factory;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.breakpoint.annotion.WebLogging;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * LoggingMethodInterceptorSupport for invokeMethod
 *
 * @author :breakpoint/赵立刚
 */
public abstract class LoggingMethodInterceptorSupport {


    private static final Logger log = LoggerFactory.getLogger(LoggingJDKMethodInterceptor.class);

    private Map<String, Object> methodMap = new HashMap<String, Object>();

    private Object METHOD_VALUE = new Object();

    // 注解信息
    private WebLogging webLogging;

    private static SimpleDateFormat pdf;

    // 代理的对象
    private Object target;

    private EasyLoggingHandle easyLoggingHandle;

    public LoggingMethodInterceptorSupport(WebLogging webLogging, Object target, EasyLoggingHandle easyLoggingHandle) {
        this.webLogging = webLogging;
        this.target = target;
        for (String method : webLogging.methods()) {
            this.methodMap.put(method, METHOD_VALUE);
        }
        this.pdf = new SimpleDateFormat(webLogging.timePattern());
        this.easyLoggingHandle = easyLoggingHandle;
    }


    protected Object invokeMethod(Object proxy, Method method, Object[] args) throws Throwable {
        if (null != methodMap.get(method.getName())) {
            log.info("接口请求数据:【{}】||方法:【{}】|| 时间:【{}】", JSONObject.toJSONString(args), method.getName(), pdf.format(new Date()));
            if (null != easyLoggingHandle) {
                easyLoggingHandle.invokeBefore(method.getName(), args);
            }
        }
        Object resVal = null;
        try {
            resVal = method.invoke(target, args);

            if (null != methodMap.get(method.getName())) {
                log.info("接口请求完成，进行返回结果：【{}】|| 完成时间:【{}】", JSONObject.toJSONString(resVal), pdf.format(new Date()));
                if (null != easyLoggingHandle) {
                    easyLoggingHandle.invokeAfter(method.getName(), args, resVal);
                }
            }

        } catch (Exception e) {
            if (null != easyLoggingHandle) {
                easyLoggingHandle.invokeOnThrowing(method.getName(), args, e);
            }
        }

        return resVal;
    }
}
