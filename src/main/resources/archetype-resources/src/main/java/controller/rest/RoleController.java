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
import ${package}.dto.RoleDto;
import ${package}.dto.validator.RoleDtoValidator;
import ${package}.exception.CustomResourceException;
import ${package}.service.RoleService;
import ${package}.util.Pagination;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
@RestController
public class RoleController {

  private static final Logger logger = LogManager.getLogger(RoleController.class);

  private RoleService roleService;
  private RoleDtoValidator roleValidator;

  private Pagination pagination;

  @Autowired
  public RoleController(RoleService roleService, RoleDtoValidator roleValidator, Pagination pagination) {
    this.roleService = roleService;
    this.roleValidator = roleValidator;
    this.pagination = pagination;
  }

  @RequestMapping(value = "/v1/roles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Object> getRoles(
      @RequestParam(defaultValue = "1", value = "page", required = false) Integer page,
      @RequestParam(defaultValue = "10", value = "size", required = false) Integer size) {
    logger.info("Fetch all roles.");

    long count = roleService.count();
    Integer startPage = pagination.start(page, size);
    Integer maxPages = pagination.maxPages(size, count);
    List<RoleDto> dtos = roleService.getAll(startPage, size);
    ResultMessage<RoleDto> result = new ResultMessage<>(page, maxPages, dtos);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @RequestMapping(value = "/v1/roles/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Object> getRole(@PathVariable("id") Long id) {
    return new ResponseEntity<>(roleService.getById(id), HttpStatus.OK);
  }


  @RequestMapping(value = "/v1/roles/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Object> updateRole(@PathVariable("id") Long id, @RequestBody @Valid RoleDto roleDto, BindingResult results) {
    logger.info("Update a role with id: " + id);

    if (roleDto.getId() == null)
      roleDto.setId(id);

    roleValidator.validate(roleDto, results);
    if (results.hasErrors()) {
      throw new CustomResourceException(results);
    }
    return new ResponseEntity<>(roleService.save(roleDto), HttpStatus.OK);
  }

  @RequestMapping(value = "/v1/roles", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Object> addRole(@RequestBody @Valid RoleDto roleDto, BindingResult results) {
    logger.info("Add a role.");

    roleValidator.validate(roleDto, results);
    if (results.hasErrors()) {
      throw new CustomResourceException(results);
    }
    return new ResponseEntity<>(roleService.save(roleDto), HttpStatus.OK);
  }

}
