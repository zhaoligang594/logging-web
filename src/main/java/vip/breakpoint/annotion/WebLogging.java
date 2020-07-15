package vip.breakpoint.annotion;

import java.lang.annotation.*;

/**
 * @author :breakpoint/赵立刚
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebLogging {
    // 需要打印日志的方法
    String[] methods() default {};
    String timePattern() default "yyyy-MM-dd HH:mm:ss";
}
