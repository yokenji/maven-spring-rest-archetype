package ${package}.controller.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ${package}.service.UserService;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
@RestController
public class ProfileController {

  private static final Logger logger = LogManager.getLogger(ProfileController.class);

  private UserService userService;

  @Autowired
  public ProfileController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Get current user.
   * 
   * @return 
   */
  @RequestMapping(value = "/v1/profile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> getUser() {
    return new ResponseEntity<>(userService.getUserProfile(), HttpStatus.OK);
  }

}
