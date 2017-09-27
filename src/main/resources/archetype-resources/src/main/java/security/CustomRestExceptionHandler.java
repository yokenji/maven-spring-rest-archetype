package ${package}.security;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mattheeuws.security.exception.ErrorResource;
import com.mattheeuws.security.exception.NotFoundException;


/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    logger.error(ex.getMessage());
    return getResponse(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED");
  }

  @ExceptionHandler({ AuthenticationException.class })
  public ResponseEntity<Object> handleAuthenticationException(final AuthenticationException ex, final WebRequest request) {
    logger.error(ex.getMessage());
    return getResponse(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED");
  }

  @ExceptionHandler({ NotFoundException.class })
  public ResponseEntity<Object> handleNotFoundException(final NotFoundException ex) {
    logger.error(ex.getMessage());
    return getResponse(HttpStatus.NOT_FOUND, "NOT_FOUND");
  }

  @ExceptionHandler({ ConstraintViolationException.class })
  public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final WebRequest request) {
    logger.error(ex.getMessage());
    return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
  }

  @ExceptionHandler({ Exception.class })
  public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
    logger.error(ex.getMessage());
    return getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR");
  }

  private ResponseEntity<Object> getResponse(HttpStatus status, String message) {
    return new ResponseEntity<>(getErrors(status, message), status);
  }

  private ErrorResource getErrors(HttpStatus status, String message) {
    return new ErrorResource(String.valueOf(status.value()), message);
  }

}
