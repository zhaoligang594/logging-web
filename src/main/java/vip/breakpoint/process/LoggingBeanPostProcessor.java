package vip.breakpoint.process;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import vip.breakpoint.annotion.WebLogging;
import vip.breakpoint.exception.MultiInterfaceBeansException;
import vip.breakpoint.factory.LoggingFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :breakpoint/赵立刚
 */
public class LoggingBeanPostProcessor implements BeanDefinitionRegistryPostProcessor, BeanPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private BeanDefinitionRegistry registry;

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // nothing to do
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        if ("myService".equals(beanName)) {
//            System.out.println(beanName);
//        }
        BeanDefinition beanDefinition = registry.getBeanDefinition(beanName);
        String beanClassName = beanDefinition.getBeanClassName();
        try {
            Class<?> oriClass = Class.forName(beanClassName);
            Class<?> targetClass = getTargetClass(oriClass);
            if (null != targetClass) {
                WebLogging webLogging = targetClass.getAnnotation(WebLogging.class);
                if (null != webLogging) {
                    Object loggingProxyObject = LoggingFactory.getLoggingProxyObject(applicationContext.getClassLoader(),
                            webLogging, bean, targetClass);
                    if (null != loggingProxyObject) {
                        return loggingProxyObject;
                    }
                }
            }
        } catch (ClassNotFoundException e) {

        }
        return bean;
    }

    // 找到对应的类
    private Class<?> getTargetClass(Class<?> oriClass) throws BeansException {
        Class<?>[] interfaces = oriClass.getInterfaces();
        List<Class<?>> candidate = new ArrayList<>();
        for (Class<?> klass : interfaces) {
            if (klass.getAnnotation(WebLogging.class) != null) candidate.add(klass);
        }
        if (candidate.size() > 1) {
            throw new MultiInterfaceBeansException("there have multi interfaces from bean");
        }
        if (candidate.size() == 0) {
            return null;
        } else {
            return candidate.get(0);
        }
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        this.registry = registry;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
