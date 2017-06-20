package ${package}.exception;

import java.util.ArrayList;
import java.util.List;

import ${package}.exception.FieldErrorResource;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
public class ErrorResource {

  private String code;
  private String message;
  private List<FieldErrorResource> fieldErrors = new ArrayList<>();

  public ErrorResource() {
  }

  public ErrorResource(String code, String message) {
    this.code = code;
    this.message = message;
  }

  /**
   * Get the code
   *
   * @return String
   */
  public String getCode() {
    return code;
  }

  /**
   * Set the code 
   *
   * @param String code
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * Get the message
   *
   * @return String
   */
  public String getMessage() {
    return message;
  }

  /**
   * Set the message 
   *
   * @param String message
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * Get the fieldErrors
   *
   * @return List<FieldErrorResource>
   */
  public List<FieldErrorResource> getFieldErrors() {
    return fieldErrors;
  }

  /**
   * Set the fieldErrors 
   *
   * @param List<FieldErrorResource> fieldErrors
   */
  public void setFieldErrors(List<FieldErrorResource> fieldErrors) {
    this.fieldErrors = fieldErrors;
  }

}
