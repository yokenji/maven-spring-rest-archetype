package ${package}.controller.rest;


import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ${package}.dto.ResultMessage;
import ${package}.dto.UserDto;
import ${package}.dto.validator.UserDtoValidator;
import ${package}.exception.CustomResourceException;
import ${package}.service.UserService;
import ${package}.util.Pagination;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
@RestController
public class UserController {

  private static final Logger logger = LogManager.getLogger(UserController.class);

  private UserService userService;
  private UserDtoValidator userValidator;

  private Pagination pagination;

  @Autowired
  public UserController(UserService userService, UserDtoValidator userValidator, Pagination pagination) {
    this.userService = userService;
    this.userValidator = userValidator;
    this.pagination = pagination;
  }

  @RequestMapping(value = "/v1/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Object> getUsers(
      @RequestParam(defaultValue = "1", value = "page", required = false) Integer page,
      @RequestParam(defaultValue = "10", value = "size", required = false) Integer size,
      @RequestParam(defaultValue = "", value = "firstName", required = false) String searchByFirstName,
      @RequestParam(defaultValue = "", value = "lastName", required = false) String searchByLastName) {
    logger.info("Fetch all users.");

    long count = userService.count(searchByFirstName, searchByLastName);
    Integer startPage = pagination.start(page, size);
    Integer maxPages = pagination.maxPages(size, count);
    List<UserDto> dtos = userService.getAll(startPage, size, searchByFirstName, searchByLastName);
    ResultMessage<UserDto> result = new ResultMessage<>(page, maxPages, dtos);

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @RequestMapping(value = "/v1/users/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Object> getUser(@PathVariable("id") Long id) {
    return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
  }

  @RequestMapping(value = "/v1/users/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Object> updateUser(@PathVariable("id") Long id, @RequestBody @Valid UserDto userDto, BindingResult results) {
    logger.info("Update a user with id: " + id);

    userValidator.validate(userDto, results);
    if (results.hasErrors()) {
      throw new CustomResourceException(results);
    }

    return new ResponseEntity<>(userService.save(userDto), HttpStatus.OK);
  }

  @RequestMapping(value = "/v1/users", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Object> addUser(@RequestBody @Valid UserDto userDto, BindingResult results) {
    return null;
  }

}
