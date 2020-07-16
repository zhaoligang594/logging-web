package vip.breakpoint.factory;

import java.lang.reflect.Method;

/**
 * 日志回调
 *
 * @author :breakpoint/赵立刚
 */
public interface EasyLoggingHandle {

    // before
    void invokeBefore(String methodName, Object[] methodArgs);

    // after
    void invokeAfter(String methodName, Object[] methodArgs, Object resVal);

}
