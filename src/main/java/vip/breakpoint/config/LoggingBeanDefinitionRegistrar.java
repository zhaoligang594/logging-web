package vip.breakpoint.config;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import vip.breakpoint.XmlEnableLoggingConfiguration;

import java.util.Properties;

/**
 * @author :breakpoint/赵立刚
 */
public class LoggingBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    // 定义自己的组件
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry) {
        System.setProperty("file.encoding", "UTF-8");
        //Properties properties = System.getProperties();
        registry.registerBeanDefinition(XmlEnableLoggingConfiguration.class.getName(),
                new RootBeanDefinition(XmlEnableLoggingConfiguration.class));

    }
}
