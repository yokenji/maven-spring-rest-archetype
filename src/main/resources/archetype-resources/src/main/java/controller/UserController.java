package ${package}.controller;


import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ${package}.dto.UserDto;
import ${package}.dto.validator.UserDtoValidator;
import ${package}.service.UserService;
import ${package}.util.Pagination;
import ${package}.util.impl.URLHelper;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
@Controller
@RequestMapping(value = "/users")
public class UserController {

  private static final Logger log = LogManager.getLogger(UserController.class);

  private Pagination pagination;
  private UserService userService;
  private UserDtoValidator userDtoValidator;

  private final String DEFAULT_USER_ROLE = "ROLE_USER";
  private final String DEFAULT_USER_LANGUAGE = "NL";

  @Autowired
  public UserController(Pagination pagination, UserService userService, UserDtoValidator userDtoValidator) {
    this.pagination = pagination;
    this.userService = userService;
    this.userDtoValidator = userDtoValidator;
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  public String getUsers(Model model,
      @RequestParam(defaultValue = "1", value = "page", required = false) Integer page,
      @RequestParam(defaultValue = "10", value = "size", required = false) Integer size,
      @RequestParam(defaultValue = "", value = "searchByFirstName", required = false) String searchByFirstName,
      @RequestParam(defaultValue = "", value = "searchByLastName", required = false) String searchByLastName) {

    log.info("Fetch all users.");

    // Calculate the pagination.
    long countUsers = userService.count(searchByFirstName, searchByLastName);
    int startPage = pagination.start(page, size);
    int maxPages = pagination.maxPages(size, countUsers);
    List<UserDto> dtos = userService.getAll(startPage, size, searchByFirstName, searchByLastName);

    String searchFilterString = "";

    if (searchByFirstName != null && !searchByFirstName.isEmpty()) {
      model.addAttribute("searchByFirstName", searchByFirstName);
      searchFilterString += String.format("&%s=%s", "searchByFirstName", URLHelper.urlEncode((searchByFirstName)));
    }

    if (searchByLastName != null && !searchByLastName.isEmpty()) {
      model.addAttribute("searchByLastName", searchByLastName);
      searchFilterString += String.format("&%s=%s", "searchByLastName", URLHelper.urlEncode((searchByLastName)));
    }

    model.addAttribute("searchFilterString", searchFilterString);
    model.addAttribute("page", page);
    model.addAttribute("totalPages", maxPages);
    model.addAttribute("users", dtos);

    return "/user/users";
  }

  @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
  public String editUser(@PathVariable("id") Long id, Model model) {
    UserDto userDto = userService.getById(id);
    model.addAttribute("user", userDto);
    model.addAttribute("userRoles", userService.getUserRoles());
    model.addAttribute("userLanguages", userService.getUserLanguages());
    return "/user/user";
  }

  @RequestMapping(value = "/add", method = RequestMethod.GET)
  public String addUser(Model model) {
    UserDto userDto = new UserDto();
    userDto.setLanguage(DEFAULT_USER_LANGUAGE);
    userDto.setRole(DEFAULT_USER_ROLE);

    model.addAttribute("user", userDto);
    model.addAttribute("userRoles", userService.getUserRoles());
    model.addAttribute("userLanguages", userService.getUserLanguages());

    return "/user/user";
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  public String saveUpdate(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result, Model model) {
    log.info("Save/Update an user.");

    // Custom user validation.
    userDtoValidator.validate(userDto, result);

    if (result.hasErrors()) {
      log.warn("Invalid user.");
      model.addAttribute("user", userDto);
      model.addAttribute("userRoles", userService.getUserRoles());
      model.addAttribute("userLanguages", userService.getUserLanguages());
      return "/user/user";
    }

    userService.save(userDto);
    return "redirect:/users";
  }

}
