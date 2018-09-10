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
      
   