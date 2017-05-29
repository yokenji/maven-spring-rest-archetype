package ${package}.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
@Controller
public class ErrorController {

  private static final Logger logger = LogManager.getLogger(ErrorController.class);

  @RequestMapping(value = "/error", method = RequestMethod.GET)
  public String errorHandler(HttpServletRequest request, Model model) {
    
    int httpErrorCode = getErrorCode(request);
    String httpErrorUrl = getErrorUrl(request);
    String httpErrorException = getErrorException(request);

    logger.error("\n" +
        "Http Error Code: " + httpErrorCode + "\n" +
        "Http Error URL: " + httpErrorUrl + "\n" + 
        "Http Error Exception: " + (httpErrorException != null ? httpErrorException : "Unknown"));

    return "/errors/error";
  }

  private int getErrorCode(HttpServletRequest httpRequest) {
    return (Integer) httpRequest
        .getAttribute("javax.servlet.error.status_code");
  }

  private String getErrorUrl(HttpServletRequest httpRequest) {
    return (String) httpRequest
        .getAttribute("javax.servlet.error.request_uri");
  }

  private String getErrorException(HttpServletRequest httpRequest) {
    return (String) httpRequest
        .getAttribute("javax.servlet.error.exception");
  }
}
