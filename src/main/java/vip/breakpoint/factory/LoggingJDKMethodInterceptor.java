package vip.breakpoint.factory;

import vip.breakpoint.annotion.WebLogging;
import vip.breakpoint.definition.ObjectMethodDefinition;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author :breakpoint/赵立刚
 */
public class LoggingJDKMethodInterceptor extends LoggingMethodInterceptorSupport implements InvocationHandler {

    public LoggingJDKMethodInterceptor(ObjectMethodDefinition methodDefinition, Object target, EasyLoggingHandle easyLoggingHandle) {
        super(methodDefinition, target, easyLoggingHandle);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return super.invokeMethod(proxy, method, args);
    }
}
