package vip.breakpoint.factory;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import vip.breakpoint.annotion.WebLogging;

import java.lang.reflect.InvocationTargetException;
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


    private Map<String, Object> methodMap = new HashMap<String, Object>();

    private Object METHOD_VALUE = new Object();

    // 注解信息
    private WebLogging webLogging;

    private static SimpleDateFormat pdf;


    private StringBuffer sb = new StringBuffer();

    // 代理的对象
    private Object target;

    private EasyLoggingHandle easyLoggingHandle;

    protected final Log logger = LogFactory.getLog(getClass());


    public LoggingMethodInterceptorSupport(WebLogging webLogging, Object target, EasyLoggingHandle easyLoggingHandle) {
        this.webLogging = webLogging;
        this.target = target;
        for (String method : webLogging.methods()) {
            this.methodMap.put(method, METHOD_VALUE);
        }
        this.pdf = new SimpleDateFormat(webLogging.timePattern());
        this.easyLoggingHandle = easyLoggingHandle;
    }

    // real invoke process
    protected Object invokeMethod(Object proxy, Method method, Object[] args) throws Throwable {
        // get methodName
        final String methodName = method.getName();
        if (null != methodMap.get(methodName)) {
            sb.delete(0, sb.length());
            sb.append("request params:【")
                    .append(JSONObject.toJSONString(args))
                    .append("】||request method:【")
                    .append(methodName)
                    .append("】|| request time :【")
                    .append(pdf.format(new Date()))
                    .append("】");
            //System.out.println(sb.toString());
            logger.info(sb.toString());
            if (null != easyLoggingHandle) {
                easyLoggingHandle.invokeBefore(methodName, args);
            }
        }
        Object resVal = null;
        try {
            resVal = method.invoke(target, args);
            if (null != methodMap.get(methodName)) {
                sb.delete(0, sb.length());
                sb.append("request params:【")
                        .append(JSONObject.toJSONString(args))
                        .append("】||request method:【")
                        .append(methodName)
                        .append("】|| complete time:【")
                        .append(pdf.format(new Date()))
                        .append("】||return val:【")
                        .append(JSONObject.toJSONString(resVal))
                        .append("】");
                logger.info(sb.toString());
                if (null != easyLoggingHandle) {
                    easyLoggingHandle.invokeAfter(methodName, args, resVal);
                }
            }

        } catch (Exception e) {
            sb.delete(0, sb.length());
            sb.append("request params:【")
                    .append(JSONObject.toJSONString(args))
                    .append("】|| method:【")
                    .append(methodName)
                    .append("】|| current time:【")
                    .append(pdf.format(new Date()))
                    .append("】");
            Throwable throwable = null;
            if (e instanceof InvocationTargetException) {
                InvocationTargetException targetException = (InvocationTargetException) e;
                throwable = targetException.getTargetException();
            } else {
                throwable = e;

            }
            sb.append("exception:【")
                    .append(JSONObject.toJSONString(throwable.getClass().getName() + ":cause:" + throwable.getMessage()))
                    .append("】");
            if (null != easyLoggingHandle) {
                easyLoggingHandle.invokeOnThrowing(method.getName(), args, throwable);
            }
            logger.error(sb.toString());
            sb.delete(0, sb.length());
            // throw exception
            throw throwable;
        }
        return resVal;
    }
}
