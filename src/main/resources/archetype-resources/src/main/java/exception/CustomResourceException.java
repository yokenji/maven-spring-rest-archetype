package ${package}.exception;

import org.springframework.validation.BindingResult;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
public class CustomResourceException extends Exception {

  private static final long serialVersionUID = 1L;

  private final BindingResult bindingResult;

  public CustomResourceException(BindingResult bindingResult) {
    this.bindingResult = bindingResult;
  }

  public BindingResult getBindingResult() {
    return bindingResult;
  }

}
