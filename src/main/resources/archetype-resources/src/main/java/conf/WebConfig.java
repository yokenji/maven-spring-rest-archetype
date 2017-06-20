package ${package}.conf;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import nz.net.ultraq.thymeleaf.LayoutDialect;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
@Configuration
@EnableWebMvc
@ComponentScan("${groupId}")
@PropertySources({
  @PropertySource(value = {"classpath:app.properties"}),
  @PropertySource(value = {"classpath:log4j2.properties"}),
  @PropertySource(value = {"classpath:database.properties"}), 
  @PropertySource(value = {"classpath:quartz.properties"}),
  @PropertySource(value = {"classpath:mail.properties"})
})
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

  private static final String ENCODING = "UTF-8";
  private static final String DEFAULT_LANGUAGE = "nl"; 
 
  private ApplicationContext applicationContext;

  /* (non-Javadoc)
   * @see org.springframework.context.ApplicationContextAware\#setApplicationContext(org.springframework.context.ApplicationContext)
   */
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @Bean
  public LocaleResolver localeResolver() {
    SessionLocaleResolver resolver = new SessionLocaleResolver();
    resolver.setDefaultLocale(new Locale(DEFAULT_LANGUAGE));
    return resolver;
  }

  /* (non-Javadoc)
   * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter\#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
    interceptor.setParamName("lang");
    registry.addInterceptor(interceptor);
  }

  private Set<ITemplateResolver> templateResolvers() {
    Set<ITemplateResolver> resolvers = new LinkedHashSet<>();
    resolvers.add(templateResolver());
    resolvers.add(emailTemplateResolver());
    return resolvers;
  }

  private ITemplateResolver templateResolver() {
    SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
    resolver.setCheckExistence(true);
    resolver.setOrder(1);
    resolver.setApplicationContext(applicationContext);
    resolver.setPrefix("/WEB-INF/thymeleaf/");
    resolver.setCacheable(false);
    resolver.setSuffix(".html");
    resolver.setTemplateMode(TemplateMode.HTML);
    resolver.setCharacterEncoding(ENCODING);
    return resolver;
  }

  @Bean
  public SpringTemplateEngine templateEngine() {
    SpringTemplateEngine engine = new SpringTemplateEngine();
    engine.setEnableSpringELCompiler(true);
    engine.setTemplateResolvers(templateResolvers());
    engine.addDialect(new LayoutDialect());
    engine.addDialect(new SpringSecurityDialect());
    return engine;
  }


  @Bean
  public ViewResolver viewResolver() {
    ThymeleafViewResolver resolver = new ThymeleafViewResolver();
    resolver.setTemplateEngine(templateEngine());
    resolver.setCharacterEncoding(ENCODING);
    resolver.setOrder(1);
    return resolver;
  }

  /**
   * Template Resolver for email templates.
   * 
   * @return
   */
   private ITemplateResolver emailTemplateResolver() {
     ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
     resolver.setOrder(2);
     resolver.setPrefix("/email/");
     resolver.setSuffix(".html");
     resolver.setTemplateMode(TemplateMode.HTML);
     resolver.setCharacterEncoding(ENCODING);
     resolver.setCacheable(false);
     return resolver;
   }

   /* (non-Javadoc)
    * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter\#addResourceHandlers(org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry)
    */
   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
     registry
       .addResourceHandler("/**")
       .addResourceLocations("/");
   }

   @Bean
   public MessageSource messageSource() {
     ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
     messageSource.setBasename("messages");
     messageSource.setDefaultEncoding(ENCODING);
     return messageSource;
   }

   @Bean(name = "localBeanValidator")
   public Validator validator() {
     return new LocalValidatorFactoryBean();
   }

//   @Bean
//   @Profile("prod")
//   public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
//     System.err.println("ERRo........");
//     // org.springframework.security.access.AccessDeniedException
//     SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
//     Properties errorMaps = new Properties();
//     //errorMaps.setProperty("org.springframework.security.access.AccessDeniedException", "/errors/error");
//     errorMaps.setProperty("NoSuchRequestHandlingMethodException", "/errors/error");
//     errorMaps.setProperty("NoHandlerFoundException", "/errors/error");
////     errorMaps.setProperty("", "/errors/error");
////     errorMaps.setProperty("", "/errors/error");
//     exceptionResolver.setExceptionMappings(errorMaps);
//     exceptionResolver.setDefaultErrorView("errors/error");
//     return exceptionResolver;
//   }

}
