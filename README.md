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
####推断Web应用类型
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
 
 ####推断引导主类
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
    