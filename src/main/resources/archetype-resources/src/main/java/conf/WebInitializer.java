package ${package}.conf;

import javax.servlet.Filter;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

  private final String ENCODING = "UTF-8";

  /* (non-Javadoc)
   * @see org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer\#getRootConfigClasses()
   */
  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class[] {
      WebConfig.class
    };
  }

  /* (non-Javadoc)
   * @see org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer\#getServletConfigClasses()
   */
  @Override
  protected Class<?>[] getServletConfigClasses() {
    return null;
  }

  /* (non-Javadoc)
   * @see org.springframework.web.servlet.support.AbstractDispatcherServletInitializer\#getServletMappings()
   */
  @Override
  protected String[] getServletMappings() {
    return new String[] {"/api/*"};
  }

  @Override
  protected Filter[] getServletFilters() {
    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
    characterEncodingFilter.setEncoding(ENCODING);
    return new Filter[] {characterEncodingFilter};
  }

  @Override
  protected DispatcherServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
      final DispatcherServlet servlet = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
      servlet.setThrowExceptionIfNoHandlerFound(true);
      return servlet;
  }

}