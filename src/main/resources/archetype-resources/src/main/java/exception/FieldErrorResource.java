package ${package}.exception;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
public class FieldErrorResource {

  private String resource;
  private String field;
  private String code;
  private String message;

  public FieldErrorResource() {
  }

  public FieldErrorResource(String resource, String field, String code, String message) {
    this.resource = resource;
    this.field = field;
    this.code = code;
    this.message = message;
  }

  /**
   * Get the resource
   *
   * @return String
   */
  public String getResource() {
    return resource;
  }

  /**
   * Set the resource 
   *
   * @param String resource
   */
  public void setResource(String resource) {
    this.resource = resource;
  }

  /**
   * Get the field
   *
   * @return String
   */
  public String getField() {
    return field;
  }

  /**
   * Set the field 
   *
   * @param String field
   */
  public void setField(String field) {
    this.field = field;
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

}
