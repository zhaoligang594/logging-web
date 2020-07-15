package com.breakpoint.vip.config;

import com.breakpoint.vip.process.LoggingBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author :breakpoint/赵立刚
 * @date : 2020/07/15
 */
public class LoggingBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    // 定义自己的组件
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry) {
        registry.registerBeanDefinition(LoggingBeanPostProcessor.class.getName(),
                new RootBeanDefinition(LoggingBeanPostProcessor.class));

    }
}
