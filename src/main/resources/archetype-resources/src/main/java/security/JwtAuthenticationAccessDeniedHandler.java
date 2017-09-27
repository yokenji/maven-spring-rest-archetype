package ${package}.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattheeuws.security.exception.ErrorResource;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
@Component
public class JwtAuthenticationAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    ObjectMapper objectMapper = new ObjectMapper();
    ErrorResource errorResource = new ErrorResource(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "UNAUTHORIZED");
    response.getWriter().write(objectMapper.writeValueAsString(errorResource));
  }

}
