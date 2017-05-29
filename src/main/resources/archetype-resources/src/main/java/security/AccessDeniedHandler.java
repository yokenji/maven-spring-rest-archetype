package ${package}.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
public class AccessDeniedHandler extends AccessDeniedHandlerImpl {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException exception) throws IOException, ServletException {
    super.handle(request, response, exception);
  }

}
