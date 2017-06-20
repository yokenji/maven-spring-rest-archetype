package ${package}.dto.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Bossuyt Fabrice <fabrice.bossuyt@gmail.com>, Original Author
 */
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

  /**
   * (?=.*\\d)  must contains one digit from 0-9
   * (?=.*[a-z]) must contains one lowercase character
   * (?=.*[A-Z]) must contains on uppercase character
   * (?=.*[@\#$%]) must contains one special symbol in the list @\#$% WILL NOT BE USED!!!
   * . match anything with previous condition checking
   * {6,20} length at least 6 characters and maximum of 20
   */
  //private final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@\#$%]).{6,20})";
  private final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";

  @Override
  public void initialize(ValidPassword constraintAnnotation) {
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
    Matcher match = pattern.matcher(value);
    return match.matches();
  }

}