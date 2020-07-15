### 一、项目解释：

* 该项目是一个日志的中间件，实现可插拔的日志打印。

* 打印日志的范围主要是对于接口的方法进行打印。

### 二、项目的配置

#### 2.1 首先在我们的配置类上开启日志功能

```java
@Configuration
@ComponentScan(basePackages = {"vip.breakpoint.bean", "vip.breakpoint.service"})
// 开启日志打印功能
@EnableLoggingConfiguration
@Import({MyAspectj.class})
public class MainConfig {
}
```

#### 2.2 在我们的接口上使用配置打印日志的方法

```java
	@WebLogging(methods = {"add"})
			public interface MyService {
    		int add(int a, int b);
  }
```

#### 2.3 执行结果

```java
....LoggingMethodInterceptor - 接口请求数据:【[1,2]】||方法:【add】|| 时间:【2020-07-15 17:13:38】
com.breakpoint.vip.aspectj.MyAspectj.pointcut.before
com.breakpoint.vip.aspectj.MyAspectj.pointcut.after
com.breakpoint.vip.aspectj.MyAspectj.afterReturning
....LoggingMethodInterceptor - 接口请求完成，进行返回结果：【3】
```

### 三、项目结构

![image-20200715171512729](pic/image-20200715171512729.png)

