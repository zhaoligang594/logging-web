package vip.breakpoint.factory;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import vip.breakpoint.annotion.WebLogging;

import java.lang.reflect.Method;

/**
 * @author :breakpoint/赵立刚
 */
public class LoggingCGlibMethodInterceptor extends LoggingMethodInterceptorSupport implements MethodInterceptor {


    public LoggingCGlibMethodInterceptor(WebLogging webLogging, Object target, EasyLoggingHandle easyLoggingHandle) {
        super(webLogging, target, easyLoggingHandle);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return super.invokeMethod(o,method,objects);
    }
}
