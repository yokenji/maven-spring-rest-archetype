package ${package}.dto.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mattheeuws.security.dto.RoleDto;
import com.mattheeuws.security.service.RoleService;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
@Component
public class RoleDtoValidator implements Validator {

  private RoleService roleService;

  private static final Logger logger = LogManager.getLogger(RoleDtoValidator.class);

  @Autowired
  public RoleDtoValidator(RoleService roleService) {
    this.roleService = roleService;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return RoleService.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    RoleDto roleDto = (RoleDto)target;

    if (roleDto.getName() != null && !roleService.isNameUnique(roleDto.getName(), roleDto.getId())) {
      logger.error("Name [" + roleDto.getName() + "] is not unique!");
      errors.rejectValue("name", "not.unique", "Name '" + roleDto.getName() + "' is not unique");
    }
  }

}
