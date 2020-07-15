package vip.breakpoint.exception;

import org.springframework.beans.BeansException;

/**
 * 多个接口的情况产生问题
 *
 * @author :breakpoint/赵立刚
 */
public class MultiInterfaceBeansException extends BeansException {
    public MultiInterfaceBeansException(String msg) {
        super(msg);
    }

    public MultiInterfaceBeansException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
