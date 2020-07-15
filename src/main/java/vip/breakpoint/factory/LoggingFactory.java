package vip.breakpoint.factory;

import vip.breakpoint.annotion.WebLogging;

import java.lang.reflect.Proxy;

/**
 * 获取执行的代理对象
 *
 * @author :breakpoint/赵立刚
 */
public class LoggingFactory {

    // 返回代理对象
    public static Object getLoggingProxyObject(ClassLoader classLoader, WebLogging webLogging, Object bean, Class<?> targetClass) {
        LoggingMethodInterceptor interceptor = new LoggingMethodInterceptor(webLogging, bean);
        return Proxy.newProxyInstance(classLoader, new Class[]{targetClass}, interceptor);
    }
}
