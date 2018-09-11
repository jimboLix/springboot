### Spring Application 准备阶段
    
   #### 配置Spring Boot Bean源
    java配置class或xml上下文配置文件集合，用于Spring boot BeanDefinitionLoader读取，
    并将配置源解析加载为Spring bean定义
    
   ##### 1.Java配置class
    用于spring 注解驱动中Java配置类，大多数是spring模式注解所标注的类，如@Configuration
    
   ##### 2.xml上下文配置文件
    传统的spring 配置文件
   
  在SpringApplication中有如下方法读取xml或者类名进行启动
  ~~~~java_holder_method_tree
  /**
  	 * Set additional sources that will be used to create an ApplicationContext. A source
  	 * can be: a class name, package name, or an XML resource location.
  	 * <p>
  	 * Sources set here will be used in addition to any primary sources set in the
  	 * constructor.
  	 * @param sources the application sources to set
  	 * @see #SpringApplication(Class...)
  	 * @see #getAllSources()
  	 */
  	public void setSources(Set<String> sources) {
  		Assert.notNull(sources, "Sources must not be null");
  		this.sources = new LinkedHashSet<>(sources);
  	}
~~~~

  #### 推断Web应用类型
    根据classpath中存在的相关实现类来推断相应的web应用类型
    web应用类型有以下三种
   - Web Reactive：WebApplicationType.REACTIVE
   - Web Servlet: WebApplicationType.SERVLET
   - 非web WebApplicationType.NONE
   
   推断web类型的方法在org.springframework.boot.SpringApplication.deduceWebApplicationType
   ~~~~java_holder_method_tree
   private WebApplicationType deduceWebApplicationType() {
   		if (ClassUtils.isPresent(REACTIVE_WEB_ENVIRONMENT_CLASS, null)
   				&& !ClassUtils.isPresent(MVC_WEB_ENVIRONMENT_CLASS, null)
   				&& !ClassUtils.isPresent(JERSEY_WEB_ENVIRONMENT_CLASS, null)) {
   			return WebApplicationType.REACTIVE;
   		}
   		for (String className : WEB_ENVIRONMENT_CLASSES) {
   			if (!ClassUtils.isPresent(className, null)) {
   				return WebApplicationType.NONE;
   			}
   		}
   		return WebApplicationType.SERVLET;
   	}
   ~~~~
 
  #### 推断引导主类
    根据main线程执行堆栈判断实际的引导主类
    
  推断方法是org.springframework.boot.SpringApplication.deduceMainApplicationClass
  
  ~~~~java_holder_method_tree
  private Class<?> deduceMainApplicationClass() {
  		try {
  			StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
  			for (StackTraceElement stackTraceElement : stackTrace) {
  				if ("main".equals(stackTraceElement.getMethodName())) {
  					return Class.forName(stackTraceElement.getClassName());
  				}
  			}
  		}
  		catch (ClassNotFoundException ex) {
  			// Swallow and continue
  		}
  		return null;
  	}
 ~~~~

#### 加载应用上下文初始化器（ApplicationContextInitializer）  
  
    上下文初始化器配置在类路径下的META-INF/spring.factories中，如
~~~~java
# Initializers
org.springframework.context.ApplicationContextInitializer=\
org.springframework.boot.autoconfigure.SharedMetadataReaderFactoryContextInitializer,\
org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLoggingListener
~~~~
    它的键是org.springframework.context.ApplicationContextInitializer，加载初始化器是通过SpringFactoriesLoader类的loadFactoryNames方法
    上下文初始化器的执行会按照一定的顺序，这个顺序可以通过@Order注解或者实现org.springframework.core.Ordered接口，重写其getOrder()方法。
    无论是使用@Order注解还是重写getOrder()方法，都会返回一个整数，数值越小优先级越高。在org.springframework.core.Ordered接口中定义了最大和最小优先级
   - 最高优先级：Ordered.HIGHEST_PRECEDENCE
   - 最小优先级： Ordered.LOWEST_PRECEDENCE
    
#### 加载应用监听器（ApplicationListener）
    应用监听器和上下文初始化器配置原理一致，都是在类路径下的META-INF/spring.factories文件中，它的键是org.springframework.context.ApplicationListener
    
###### SpringFactoriesLoader加载META-INF/spring.factories中配置类小结
    SpringFactoriesLoader加载配置类会以父类类型进行加载，同时会根据Orderd的顺序进行加载

### SpringApplication运行阶段
    SpringApplication运行阶段主要有以下重要的过程
   - 加载SpringApplication运行监听器
   - 运行SpringApplication运行监听器
   - 监听Spring Boot、Spring事件
   - 创建应用上下文、Environment
   - 失败：故障分析报告
   - 回调，CommandLineRunner、ApplicationRunner
   
   SpringApplicationRunListeners监听的方法
   
   监听方法 | 所属阶段说明 | spring boot起始版本
   ---|---|---
   starting() | spring应用刚刚启动 | 1.0
   environmentPrepared(ConfigurableEnvironment) | ConfigurableEnvironment准备妥当，允许进行调整  |1.0
   contextPrepared(ConfigurableApplicationContext) | ApplicationContext准备妥当，但资源尚未加载 | 1.0
   contextLoaded(ConfigurableApplicationContext) | ApplicationContext已经装载，但是还未启动 |1.0
   started(ConfigurableApplicationContext)|ApplicationContext已经启动，且Spring Bean已经初始化完成|2.0
   running(ConfigurableApplicationContext)|Spring应用已经运行|2.0
   failed(ConfigurableApplicationContext, Throwable )|应用启动失败|2.0
   
   #### EventPublishingRunListener
    Spring boot中使用的运行监听器是org.springframework.boot.context.event.EventPublishingRunListener
    EventPublishingRunListener监听方法与Spring Boot事件的对应关系
    
   监听方法 | Spring Boot事件 | Spring Boot起始版本
    ---|---|---|
    starting()|ApplicationStartingEvent|1.5
   environmentPrepared(ConfigurableEnvironment) | ApplicationEnvironmentPreparedEvent | 1.0
   contextLoaded(ConfigurableApplicationContext)|ApplicationPreparedEvent| 1.0
   started(ConfigurableApplicationContext)|ApplicationStartedEvent|2.0
   running(ConfigurableApplicationContext)|ApplicationReadyEvent|2.0
   failed(ConfigurableApplicationContext, Throwable)|ApplicationFailedEvent|1.0
   
  ##### 创建应用上下文及创建Environment
    
    在SpringApplication类的run方法中会创建上下文对象及创建Environment对象。
    创建上下文对象的过程如下：
    
   ###### 1.获取SpringApplicationRunner对象并启动
~~~~java_holder_method_tree
    SpringApplicationRunListeners listeners = getRunListeners(args);
    listeners.starting();
~~~~ 
 ###### 2.准备Environment，并回调SpringApplicationRunner的environmentPrepared方法
    应用会根据推断出的web类型创建对应的Environment,Environment有如下几种类型：
 - 1.org.springframework.web.context.support.StandardServletEnvironment
 - 2.org.springframework.core.env.StandardEnvironment
###### 3.创建上下文对象
    应用会根据推断出的web应用类型创建相应的上下文对象，有如下三种类型的上下文对象
   - 1.Web应用：AnnotationConfigServletWebServerApplicationContext
   - 2.Web Reactive：AnnotationConfigReactiveWebServerApplicationContext
   - 3.非web应用： AnnotationConfigApplicationContext
###### 4.准备上下文对象
    准备上下文对象主要是向Context中设置Environment和设置SpringApplicationRunListener并调用其contextPrepared方法
##### 5.刷新上下文对象
### Web MVC
#### WebMvcConfigurer自定义组件
    如果即想保留Spring Boot MVC的特性，又想要添加自定义的MVC配置（如：拦截器等），可以建一个WebMvcConfigurer类型的配置类，在这个配置
    类上添加@Configuration注解。如果想全面接管Spring MVC，则在自定义的配置类中添加@EnableWebMvc注解（不推荐）
    
    
 
  