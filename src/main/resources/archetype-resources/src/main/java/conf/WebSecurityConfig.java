package ${package}.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

import ${package}.model.type.UserRole;
import ${package}.security.AccessDeniedHandler;
import ${package}.security.CustomAuthenticationSuccessHandler;
import ${package}.security.CustomUserDetailsService;

import ${package}.security.CustomAuthenticationSuccessHandler;
import ${package}.security.CustomUserDetailsService;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) //securedEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private CustomUserDetailsService customUserDetailsService;
  private CustomAuthenticationSuccessHandler successHandler;

  /*@Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    // TODO: change this to your preferred authentication.
    auth
      .inMemoryAuthentication()
        .withUser("user").password("password").roles("USER");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // TODO: change this to your preferred configuration.
    http
      .authorizeRequests()
        .anyRequest().authenticated()
        .and()
      .formLogin()
        .and()
      .httpBasic();
  }*/
  
  @Autowired
  public WebSecurityConfig(CustomUserDetailsService customUserDetailsService, CustomAuthenticationSuccessHandler successHandler) {
    this.customUserDetailsService = customUserDetailsService;
    this.successHandler = successHandler;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customUserDetailsService);
    auth.authenticationProvider(authenticationProvider());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
        .expressionHandler(webExpressionHandler())
        .antMatchers("/resources/**").permitAll()
        .antMatchers("/login*").permitAll()
        .antMatchers("/users/**").access("hasRole('ROLE_ADMIN')")
        .anyRequest().authenticated();

    http
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
      .sessionFixation()
        .newSession()
        .invalidSessionUrl("/login?error=invalidSession")
        .sessionAuthenticationErrorUrl("/login?alreadyLogin")
      .maximumSessions(1)
      .maxSessionsPreventsLogin(false)
      .expiredUrl("/login?error=sessionExpired");

    http
      .formLogin().failureUrl("/login?error=authFailed")
      .defaultSuccessUrl("/dashboard", true)
      .successHandler(successHandler)
      .loginPage("/login")
      .permitAll()
//      .and()
//      .httpBasic()
      .and()
      .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login?success=logOut")
        .deleteCookies("JSESSIONID")
        .invalidateHttpSession(false)
        .and()
      .csrf().disable();

    http
      .exceptionHandling()
      .accessDeniedHandler(accessDeniedHandler());
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(customUserDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public RoleHierarchy roleHierarchy() {
    RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
    roleHierarchy.setHierarchy(UserRole.roleHierarchy());
    return roleHierarchy;
  }

  private SecurityExpressionHandler<FilterInvocation> webExpressionHandler() {
    DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
    defaultWebSecurityExpressionHandler.setRoleHierarchy(roleHierarchy());
    return defaultWebSecurityExpressionHandler;
  }

  @Bean
  public AccessDeniedHandler accessDeniedHandler() {
    AccessDeniedHandler accessDeniedHandler = new AccessDeniedHandler();
    return accessDeniedHandler;
  }
}
