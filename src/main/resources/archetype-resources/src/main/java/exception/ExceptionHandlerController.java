package ${package}.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 * 
 * Important note: 
 *   @Valid annotation will result in throwing a MethodArgumentNotValidException.
 *   @Validated annotation will result in throwing a ConstraintViolationException.
 */
@ControllerAdvice
public class ExceptionHandlerController {

  private MessageSource messageSource;

  @Autowired
  public ExceptionHandlerController(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  @ExceptionHandler({MethodArgumentNotValidException.class, CustomResourceException.class})
  public ErrorResource handleValidationExceptions(HttpServletRequest req, Exception ex) {
    ErrorResource errorResource = new ErrorResource();
    errorResource.setCode("resource.input.not.valid");
    errorResource.setMessage(localizeErrorMessage("resource.input.not.valid"));

    if (ex instanceof MethodArgumentNotValidException) {
      BindingResult result = ((MethodArgumentNotValidException) ex).getBindingResult();
      errorResource
        .getFieldErrors()
          .addAll(getFieldErrors(result.getFieldErrors()));
    }

    if (ex instanceof CustomResourceException) {
      BindingResult result = ((CustomResourceException) ex).getBindingResult();
      errorResource
        .getFieldErrors()
        .addAll(getFieldErrors(result.getFieldErrors()));
    }
    return errorResource;
  }

  /**
   * 
   * @param List<FieldError> fieldErrors
   * @return List<FieldErrorResource>
   */
  private List<FieldErrorResource> getFieldErrors(List<FieldError> fieldErrors) {
    List<FieldErrorResource> retval = new ArrayList<>();
    FieldErrorResource fieldErrorResource = null;

    for (FieldError fieldError : fieldErrors) {
      System.out.println(fieldError.getField());
      fieldErrorResource = new FieldErrorResource();
      fieldErrorResource.setResource(fieldError.getObjectName());
      fieldErrorResource.setField(fieldError.getField());
      fieldErrorResource.setCode(fieldError.getCode());
      fieldErrorResource.setMessage(localizeErrorMessage(fieldError.getCode()));
      retval.add(fieldErrorResource);
    }

    return retval;
  }

  /**
   * Retrieves the appropriate localized error message.
   * 
   * @param String errorCode
   * @return String
   */
  private String localizeErrorMessage(String errorCode) {
    Locale locale = LocaleContextHolder.getLocale();
    String errorMessage = messageSource.getMessage(errorCode, null, locale);
    return errorMessage;
  }

}
