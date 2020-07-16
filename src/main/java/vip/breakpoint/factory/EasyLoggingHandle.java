package vip.breakpoint.factory;

import java.lang.reflect.Method;

/**
 * 日志回调
 *
 * @author :breakpoint/赵立刚
 */
public interface EasyLoggingHandle {

    // before
    void invokeBefore(Object proxy, Method method, Object[] args);

    // after
    void invokeAfter(Object proxy, Method method, Object[] args, Object resVal);

}
