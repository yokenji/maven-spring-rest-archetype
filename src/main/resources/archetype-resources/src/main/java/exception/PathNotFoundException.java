package ${package}.exception;
/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
public class PathNotFoundException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Default constructor.
   */
  public PathNotFoundException() {
    super();
  };

  /**
   * Constructs a new Exception with specified message.
   * 
   * @param String message
   */
  public PathNotFoundException(String message) {
    super(message);
  }

  /**
   * Constructs a new Exception with specified details and cause.
   * 
   * @param String message
   * @param Throwable cause
   */
  public PathNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new Exception with a cause.
   * 
   * @param Throwable cause
   */
  public PathNotFoundException(Throwable cause) {
    super(cause);
  }

}
