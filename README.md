### 一、项目解释：

#### 1.1 项目的主要功能

* 该项目是一个日志的中间件，实现可插拔的日志打印。

* 打印日志的范围主要是对于接口的方法进行打印。

### 二、项目的配置

#### 2.1 引入依赖

最新版本：

```xml
<dependency>
  <groupId>vip.breakpoint</groupId>
  <artifactId>logging-web</artifactId>
  <version>0.1.0</version>
</dependency>
```

#### 2.2 首先在我们的配置类上开启日志功能

```java
@Configuration
@ComponentScan(basePackages = {"vip.breakpoint.bean", "vip.breakpoint.service"})
// 开启日志打印功能
@EnableLoggingConfiguration
@Import({MyAspectj.class})
public class MainConfig {
}
```

#### 2.3 在我们的接口上使用配置打印日志的方法

```java
@WebLogging(methods = {"add"})
public interface MyService {
    int add(int a, int b);
}
```

#### 2.4 执行结果

```java
....LoggingMethodInterceptor - 接口请求数据:【[1,2]】||方法:【add】|| 时间:【2020-07-15 17:13:38】
.
.
....LoggingMethodInterceptor - 接口请求完成，进行返回结果：【3】
```

#### 2.5 自定义回调

> 这个部分可以写自己的业务逻辑，比如，可以使用MQ存储处理的日志。

##### 2.5.1 创建实现类并实现EasyLoggingHandle

```java
@Service
public class MyEasyLoggingHandle implements EasyLoggingHandle {

    @Override
    public void invokeBefore(Object proxy, Method method, Object[] args) {
        System.out.println("vip.breakpoint.service.MyEasyLoggingHandle.invokeBefore");
    }

    @Override
    public void invokeAfter(Object proxy, Method method, Object[] args, Object resVal) {
        System.out.println("vip.breakpoint.service.MyEasyLoggingHandle.invokeAfter");
    }
}
```

### 2.5.2 执行结果

```shell
....LoggingJDKMethodInterceptor - 接口请求数据:【[12,3]】||方法:【sub】|| 时间:【2020-07-16 11:09:01】
vip.breakpoint.service.MyEasyLoggingHandle.invokeBefore
.
.
.
.....LoggingJDKMethodInterceptor - 接口请求完成，进行返回结果：【9】|| 完成时间:【2020-07-16 11:09:01】
vip.breakpoint.service.MyEasyLoggingHandle.invokeAfter
```

### 三、项目依赖

* JDK 1.8

```xml
				<dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
        <!--    spring    -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context-support</artifactId>
            <version>5.1.9.RELEASE</version>
            <scope>provided</scope>
        </dependency>
        <!--  log -->
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-core</artifactId>
            <version>1.2.3</version>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.2.3</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.28</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.8</version>
        </dependency>
        <!-- json   -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.46</version>
        </dependency>
        <!--  cglib  -->
        <!-- https://mvnrepository.com/artifact/cglib/cglib -->
        <dependency>
            <groupId>cglib</groupId>
            <artifactId>cglib</artifactId>
            <version>2.2.2</version>
        </dependency>
```



### 四、项目结构

![image-20200715171512729](pic/image-20200715171512729.png)

