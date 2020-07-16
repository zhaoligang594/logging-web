package vip.breakpoint.annotion;

import vip.breakpoint.factory.LoggingLevel;

import java.lang.annotation.*;

/**
 * @author :breakpoint/赵立刚
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebLogging {

    // logging method 需要打印日志的方法
    String[] methods() default {};

    // date pattern 日期格式
    String timePattern() default "yyyy-MM-dd HH:mm:ss";

    // is all to print
    LoggingLevel logLevel() default LoggingLevel.ALL;

}
