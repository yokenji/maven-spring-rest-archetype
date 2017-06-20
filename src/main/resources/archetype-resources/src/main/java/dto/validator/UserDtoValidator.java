package ${package}.dto.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ${package}.dto.UserDto;
import ${package}.service.UserService;

/**
 * @author Bossuyt Fabrice <fabrice.bossuyt@gmail.com>, Original Author
 */
@Component
public class UserDtoValidator implements Validator {

  private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";

  private UserService userService;

  private static final Logger logger = LogManager.getLogger(UserDtoValidator.class);

  @Autowired
  public UserDtoValidator(UserService userService) {
    this.userService = userService;
  }

  /* (non-Javadoc)
   * @see org.springframework.validation.Validator\#supports(java.lang.Class)
   */
  @Override
  public boolean supports(Class<?> clazz) {
    return UserDto.class.isAssignableFrom(clazz);
  }
  
  /* (non-Javadoc)
   * @see org.springframework.validation.Validator\#validate(java.lang.Object, org.springframework.validation.Errors)
   */
  @Override
  public void validate(Object target, Errors errors) {
    UserDto userDto = (UserDto)target;
    Boolean rejectPassword = false;

    if (userDto.getId() == null || (userDto.getId() != null && !userDto.getPassword().isEmpty())){
      rejectPassword = !isPasswordValid(userDto.getPassword());
    }

    if (rejectPassword == true)
      errors.rejectValue("password", "userDto.validator.password.invalid");

    // Check if the given login is unique.
    if (userDto.getLogin() != null) {
      if (!userService.isLoginUnique(userDto.getLogin(), userDto.getId())) {
        logger.error("Login [" + userDto.getLogin() + "] is not unique!");
        errors.rejectValue("login", "not.unique");
      }
    }

    // Check if the 
  }

  private Boolean isPasswordValid(String value) {
    Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
    Matcher match = pattern.matcher(value);
    return match.matches();
  }

}
