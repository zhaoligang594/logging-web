package vip.breakpoint.factory;

import java.lang.reflect.Method;

/**
 * 日志回调
 *
 * @author :breakpoint/赵立刚
 */
public interface EasyLoggingHandle {

    /**
     * before invoke method process
     *
     * @param methodName is methodName
     * @param methodArgs is req args
     */
    void invokeBefore(String methodName, Object[] methodArgs);

    /**
     * after invoke method process
     *
     * @param methodName is methodName
     * @param methodArgs is req args
     * @param resVal     is return value
     */
    void invokeAfter(String methodName, Object[] methodArgs, Object resVal);

    /**
     * when throw exception
     *
     * @param methodName is methodName
     * @param methodArgs is req args
     * @param e          is throws exception
     */
    void invokeOnThrowing(String methodName, Object[] methodArgs, Throwable e);

}
