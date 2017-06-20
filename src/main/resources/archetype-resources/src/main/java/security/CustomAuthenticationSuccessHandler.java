package ${package}.security;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import ${package}.model.User;
import ${package}.repository.UserRepository;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private static Logger logger = LogManager.getLogger(CustomAuthenticationSuccessHandler.class);

  private LocaleResolver localResolver;
  private UserRepository userRepository;
  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  @Autowired
  public CustomAuthenticationSuccessHandler(LocaleResolver localResolver, UserRepository userRepository) {
    this.localResolver = localResolver;
    this.userRepository = userRepository;
  }

  /* (non-Javadoc)
   * @see org.springframework.security.web.authentication.AuthenticationSuccessHandler\#onAuthenticationSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
   */
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
    handle(request, response, auth);
    clearAuthenticationAttributes(request);
  }

  protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException {
    String targetUrl = determineTargetUrl(auth);
    if (response.isCommitted()) {
      logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
      return;
    }

    setUserPrefferedLanguage(request, response, auth);
    redirectStrategy.sendRedirect(request, response, targetUrl);
  }

  /**
   * Set the default language.
   * 
   * @param HttpServletRequest request
   * @param httpServletResponse response
   * @param Authentication auth
   */
  protected void setUserPrefferedLanguage(HttpServletRequest request, HttpServletResponse response, Authentication auth) {
    logger.debug("Fetch en load the default language for this user.");
    UserDetails userDetails = (UserDetails) auth.getPrincipal();
    User user = userRepository.findByLogin(userDetails.getUsername());
    if (user != null) {
      localResolver.setLocale(request, response, new Locale(user.getUserLanguage().name()));
    }
    logger.debug("Default language: " + user.getUserLanguage().name() + " set for user: " + user.getLogin());
  }

  /**
   * 
   * @param HttpServletRequest request
   */
  protected void clearAuthenticationAttributes(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session == null)
      return;
    session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
  }

  /**
   * The default for all users is default.
   * 
   * @param Authentication authentication
   * @return
   */
  protected String determineTargetUrl(Authentication authentication) {
    return "/dashboard";
  }

  public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
    this.redirectStrategy = redirectStrategy;
  }

  protected RedirectStrategy getRedirectStrategy() {
    return redirectStrategy;
  }

}
