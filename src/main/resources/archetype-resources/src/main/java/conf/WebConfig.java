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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
    .allowedHeaders("*")
    .allowedMethods("*");
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
      .addResourceHandler("swagger-ui.html")
      .addResourceLocations("classpath:/META-INF/resources/");
    registry
      .addResourceHandler("/webjars/**")
      .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }

   @Bean(name = "localBeanValidator")
   public Validator validator() {
     return new LocalValidatorFactoryBean();
   }

}
