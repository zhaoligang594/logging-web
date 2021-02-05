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
import vip.breakpoint.definition.ObjectMethodDefinition;
import vip.breakpoint.factory.EasyLoggingHandle;
import vip.breakpoint.factory.LoggingFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author :breakpoint/赵立刚
 */
public class LoggingBeanPostProcessor implements BeanDefinitionRegistryPostProcessor, BeanPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;
    private BeanDefinitionRegistry registry;
    private ConfigurableListableBeanFactory beanFactory;
    private EasyLoggingHandle easyLoggingHandle;
    private Map<String, Object> beanNamesMap = new HashMap<String, Object>();
    private Object BEAN_NAME_OBJECT = new Object();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (null != beanNamesMap.get(beanName)) {
            Class<?> oriClass = bean.getClass();
            ObjectMethodDefinition methodDefinition = new ObjectMethodDefinition();
            this.setMethodDefinition(oriClass, methodDefinition);
            if (methodDefinition.isShouldProxy()) {
                try {
                    easyLoggingHandle = applicationContext.getBean(EasyLoggingHandle.class);
                } catch (BeansException e) {
                    easyLoggingHandle = null;
                }
                Object loggingProxyObject = LoggingFactory.getLoggingCGLibProxyObject(applicationContext.getClassLoader(),
                        methodDefinition, bean, oriClass, easyLoggingHandle);
                if (null != loggingProxyObject) {
                    return loggingProxyObject;
                }
            }
        }
        return bean;
    }

    private void setMethodDefinition(Class<?> clazz, ObjectMethodDefinition methodDefinition) {
        if (clazz != Object.class) {
            WebLogging webLogging = clazz.getAnnotation(WebLogging.class);
            if (null != webLogging) {
                methodDefinition.addWebLogging(clazz, webLogging);
            }
            Class<?>[] interfaces = clazz.getInterfaces();
            for (Class<?> klass : interfaces) {
                this.setMethodDefinition(klass, methodDefinition);
            }
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
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            beanNamesMap.put(beanDefinitionName, BEAN_NAME_OBJECT);
        }
    }
}
