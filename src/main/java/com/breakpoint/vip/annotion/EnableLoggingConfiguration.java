package com.breakpoint.vip.annotion;

import com.breakpoint.vip.config.LoggingBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author :breakpoint/赵立刚
 * @date : 2020/07/15
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({LoggingBeanDefinitionRegistrar.class})
public @interface EnableLoggingConfiguration {

}
