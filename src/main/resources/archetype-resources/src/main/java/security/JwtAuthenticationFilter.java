package ${package}.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import ${package}.exception.ErrorResource;
import ${package}.exception.InvalidTokenException;

import io.jsonwebtoken.JwtException;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
@Component
public class JwtAuthenticationFilter extends GenericFilterBean {

  private TokenAuthenticationService authService;

  @Autowired
  public JwtAuthenticationFilter(TokenAuthenticationService authService) {
    this.authService = authService;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    try {

      Authentication auth = authService.getAuthentication((HttpServletRequest)request);
      if (auth != null)
        SecurityContextHolder.getContext().setAuthentication(auth);

    } catch (JwtException | InvalidTokenException ex) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        ErrorResource errorResource = new ErrorResource(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "UNAUTHORIZED");
        response.getWriter().write(objectMapper.writeValueAsString(errorResource));
        return;
    }

    chain.doFilter(request, response);
    SecurityContextHolder.getContext().setAuthentication(null);
  }

}
