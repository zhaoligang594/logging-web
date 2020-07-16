package vip.breakpoint.factory;

import net.sf.cglib.proxy.Enhancer;
import vip.breakpoint.annotion.WebLogging;

import java.lang.reflect.Proxy;

/**
 * 获取执行的代理对象
 *
 * @author :breakpoint/赵立刚
 */
public final class LoggingFactory {

    // jdk proxy
    public static Object getLoggingJDKProxyObject(ClassLoader classLoader, WebLogging webLogging,
                                                  Object bean, Class<?> targetClass, EasyLoggingHandle easyLoggingHandle) {
        LoggingJDKMethodInterceptor interceptor = new LoggingJDKMethodInterceptor(webLogging, bean, easyLoggingHandle);
        return Proxy.newProxyInstance(classLoader, new Class[]{targetClass}, interceptor);
    }

    // cglib proxy
    public static Object getLoggingCGLibProxyObject(ClassLoader classLoader, WebLogging webLogging,
                                                    Object bean, Class<?> targetClass, EasyLoggingHandle easyLoggingHandle) {

        LoggingCGlibMethodInterceptor interceptor = new LoggingCGlibMethodInterceptor(webLogging, bean, easyLoggingHandle);

        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(targetClass);

        enhancer.setCallback(interceptor);

        return enhancer.create();
    }
}
