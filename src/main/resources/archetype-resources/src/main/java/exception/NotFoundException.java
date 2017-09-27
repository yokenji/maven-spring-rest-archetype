package ${package}.exception;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
public class NotFoundException extends RuntimeException  {

  private static final long serialVersionUID = 1L;

  /**
   * Default constructor.
   */
  public NotFoundException() {
    super();
  }

  /**
   * Constructs a new Exception with specified message.
   * 
   * @param String message
   */
  public NotFoundException(String message) {
    super(message);
  }

  /**
   * Constructs a new Exception with specified details and cause.
   * 
   * @param String message
   * @param Throwable cause
   */
  public NotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new Exception with a cause.
   * 
   * @param Throwable cause
   */
  public NotFoundException(Throwable cause) {
    super(cause);
  }

}
