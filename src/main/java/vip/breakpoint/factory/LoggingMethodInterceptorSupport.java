package vip.breakpoint.factory;

import static vip.breakpoint.executor.WeblogThreadPoolExecutor.executeDoLog;

import com.alibaba.fastjson.JSONObject;
import vip.breakpoint.annotion.WebLogging;
import vip.breakpoint.definition.ObjectMethodDefinition;
import vip.breakpoint.log.LoggingLevel;
import vip.breakpoint.log.WebLogFactory;
import vip.breakpoint.log.adaptor.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author :breakpoint/赵立刚
 */
public abstract class LoggingMethodInterceptorSupport {

    private ObjectMethodDefinition methodDefinition;

    private Object delegate;

    private EasyLoggingHandle easyLoggingHandle;

    protected final Logger logger = WebLogFactory.getLogger(getClass(), LoggingLevel.TRACE);

    public LoggingMethodInterceptorSupport(ObjectMethodDefinition methodDefinition, Object delegate,
                                           EasyLoggingHandle easyLoggingHandle) {
        this.methodDefinition = methodDefinition;
        this.delegate = delegate;
        this.easyLoggingHandle = easyLoggingHandle;
    }

    // real invoke process
    protected Object invokeMethod(Object proxy, Method method, Object[] args) throws Throwable {
        final String methodName = method.getName();
        // return val
        Object resVal = null;
        if (methodDefinition.isHaveMethod(methodName)) {
            WebLogging webLogging = methodDefinition.getWebLoggingByMethod(methodName);
            SimpleDateFormat sdf = new SimpleDateFormat(webLogging.timePattern());
            StringBuffer sb = new StringBuffer();
            sb.append("request params:【")
                    .append(JSONObject.toJSONString(args))
                    .append("】||request method:【")
                    .append(methodName)
                    .append("】|| request time :【")
                    .append(sdf.format(new Date()))
                    .append("】");
            logger.info(sb.toString());
            if (null != easyLoggingHandle) {
                executeDoLog(() -> {
                    easyLoggingHandle.invokeBefore(methodName, args);
                });
            }
            try {
                resVal = method.invoke(delegate, args);
                sb.delete(0, sb.length());
                sb.append("request params:【")
                        .append(JSONObject.toJSONString(args))
                        .append("】||request method:【")
                        .append(methodName)
                        .append("】|| complete time:【")
                        .append(sdf.format(new Date()))
                        .append("】||return val:【")
                        .append(JSONObject.toJSONString(resVal))
                        .append("】");
                logger.info(sb.toString());
                if (null != easyLoggingHandle) {
                    executeDoLog(resVal, (result) -> {
                        easyLoggingHandle.invokeAfter(methodName, args, result);
                    });
                }
            } catch (Exception e) {
                sb.delete(0, sb.length());
                sb.append("request params:【")
                        .append(JSONObject.toJSONString(args))
                        .append("】|| method:【")
                        .append(methodName)
                        .append("】|| current time:【")
                        .append(sdf.format(new Date()))
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
        } else {
            resVal = method.invoke(delegate, args);
        }
        return resVal;
    }
}
