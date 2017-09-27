package ${package}.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ${package}.security.JwtAuthenticationAccessDeniedHandler;
import ${package}.security.JwtAuthenticationEntryPoint;
import ${package}.security.JwtAuthenticationFilter;
import ${package}.security.CustomUserDetailsService;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private CustomUserDetailsService customUserDetailsService;
  private JwtAuthenticationFilter jwtAuthFilter;
  private JwtAuthenticationAccessDeniedHandler accessDeniedHandler;
  private JwtAuthenticationEntryPoint authenticationEntryPoint;

  @Autowired
  public WebSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtAuthenticationFilter jwtAuthFilter, 
      JwtAuthenticationAccessDeniedHandler accessDeniedHandler,
      JwtAuthenticationEntryPoint authenticationEntryPoint) {
    this.customUserDetailsService = customUserDetailsService;
    this.jwtAuthFilter = jwtAuthFilter;
    this.accessDeniedHandler = accessDeniedHandler;
    this.authenticationEntryPoint = authenticationEntryPoint;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customUserDetailsService);
    auth.authenticationProvider(authenticationProvider());
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .csrf()
      .disable()
      .cors();

    http
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http
      .exceptionHandling()
      .accessDeniedHandler(accessDeniedHandler)
      .authenticationEntryPoint(authenticationEntryPoint);

    http
      .authorizeRequests()
      .antMatchers("/api/v2/api-docs",
          "/api/configuration/ui",
          "/api/swagger-resources/**",
          "/api/configuration/**",
          "/api/swagger-ui.html",
          "/api/webjars/**").permitAll()
      .antMatchers("/api/v1/auth/**").permitAll()
      .anyRequest().authenticated();

    http
      .addFilterBefore(jwtAuthFilter,
          UsernamePasswordAuthenticationFilter.class);
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

}
