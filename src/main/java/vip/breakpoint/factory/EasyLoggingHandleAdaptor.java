package vip.breakpoint.factory;

/**
 *
 * @author :breakpoint/赵立刚
 */
public abstract class EasyLoggingHandleAdaptor implements EasyLoggingHandle {

    @Override
    public void invokeBefore(String methodName, Object[] methodArgs) {
        // for subclass implements
    }

    @Override
    public void invokeAfter(String methodName, Object[] methodArgs, Object resVal) {
        // for subclass implements
    }

    @Override
    public void invokeOnThrowing(String methodName, Object[] methodArgs, Throwable e) throws Throwable {
        // for subclass implements
    }
}
