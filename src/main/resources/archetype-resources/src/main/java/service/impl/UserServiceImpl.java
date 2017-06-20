package ${package}.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${package}.dto.UserDto;
import ${package}.model.User;
import ${package}.model.type.UserLanguage;
import ${package}.model.type.UserRole;
import ${package}.security.SpringSecurityService;
import ${package}.service.UserService;
import ${package}.repository.UserRepository;


/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private SpringSecurityService springSecurityService;

  private static final Logger log = LogManager.getLogger(UserServiceImpl.class);

  @Autowired
  public UserServiceImpl(UserRepository userRepository,SpringSecurityService springSecurityService) {
    this.userRepository = userRepository;
    this.springSecurityService = springSecurityService;
  }

  /* (non-Javadoc)
   * @see ${package}.service.BaseService\#save(java.lang.Object)
   */
  @Override
  public UserDto save(UserDto dto) {
    UserDto retval = null;

    User user = userRepository.save(
        mapTo(dto));

    if (user != null)
      retval = mapTo(user);

    return retval;
  }

  /* (non-Javadoc)
   * @see ${package}.service.BaseService\#getById(java.lang.Long)
   */
  @Override
  public UserDto getById(Long id) {
    log.info("Find User by Id: " + id);
    return mapTo(
        userRepository.findById(id));
  }
  
  /* (non-Javadoc)
   * @see ${package}.service.BaseService\#getAll()
   */
  @Override
  public List<UserDto> getAll() {
    log.info("Find all users.");
    return mapTo(
        userRepository.findAll()
        );
  }

  /* (non-Javadoc)
   * @see ${package}.service.BaseService\#getAll(java.lang.Integer, java.lang.Integer, java.lang.String)
   */
  @Override
  public List<UserDto> getAll(Integer offset, Integer max) {
    log.info("Find all Users, Offset: " + offset + ", Max: " + max);
    return mapTo(
        userRepository.findAll(offset, max)
        ); 
  }

  /*
   * (non-Javadoc)
   * @see ${package}.service.BaseService\#count()
   */
  @Override
  public Long count() {
    return userRepository.count();
  }

  /**
   * Map a DTO to an entity.
   * 
   * @param UserDto userDto
   * @return User
   */
  private User mapTo(UserDto userDto) {
    log.info("Map an UserDto to an User entity.");
    User user = userRepository.findById(userDto.getId());
    if (user == null)
      user = new User();

    if (userDto.getPassword() != null && !userDto.getPassword().isEmpty())
      user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));

    user.setFirstName(userDto.getFirstName());
    user.setLastName(userDto.getLastName());
    user.setEmployeeNumber(userDto.getEmployeeNumber());
    user.setEmail(userDto.getEmail());
    user.setLogin(userDto.getLogin());
    user.setActivated(userDto.getActivated());
    user.setRole(UserRole.userRole(userDto.getRole()));
    user.setUserLanguage(UserLanguage.userLanguage(userDto.getLanguage()));

    return user;
  }

  /**
   * Map an entity to DTO.
   * 
   * @param User user
   * @return UserDto
   */
  private UserDto mapTo(User user) {
    log.debug("Map an User entity to an UserDto.");
    UserDto dto = new UserDto(
        user.getId(),
        user.getFirstName(),
        user.getLastName(),
        user.getEmployeeNumber(),
        user.getLogin(),
        null,
        user.getActivated(),
        user.getRole().name(),
        user.getUserLanguage().name(),
        user.getEmail());

    return dto;
  }

  /**
   * Map entities to DTO's
   * 
   * @param List<User> users
   * @return List<UserDto>
   */
  private List<UserDto> mapTo(List<User> users) {
    List<UserDto> dtos = new ArrayList<>();
    if (users == null)
      return dtos;
    for (User user : users) {
      dtos.add(mapTo(user));
    }
    return dtos;
  }

  /*
   * (non-Javadoc)
   * @see ${package}.service.UserService\#getUserRoles()
   */
  @Override
  public List<String> getUserRoles() {
    List<String> userRoles = new ArrayList<>();
    for (UserRole role : UserRole.values()) {
      userRoles.add(role.name());
    }
    return userRoles;
  }

  /*
   * (non-Javadoc)
   * @see ${package}.service.UserService\#getUserLanguages()
   */
  @Override
  public List<String> getUserLanguages() {
    List<String> userLanguages = new ArrayList<>();
    for (UserLanguage language : UserLanguage.values()) {
      userLanguages.add(language.name());
    }
    return userLanguages;
  }

  /*
   * (non-Javadoc)
   * @see ${package}.service.UserService\#isLoginUnique(java.lang.String, java.lang.Long)
   */
  @Override
  public Boolean isLoginUnique(String login, Long id) {
    return userRepository.isLoginUnique(login, id);
  }

  /*
   * (non-Javadoc)
   * @see ${package}.service.UserService\#getByLogin(java.lang.String)
   */
  @Override
  public UserDto getByLogin(String login) {
    return 
        mapTo(userRepository.findByLogin(login));
  }

  /*
   * (non-Javadoc)
   * @see ${package}.service.UserService\#count(java.lang.String, java.lang.String)
   */
  @Override
  public Long count(String searchByFirstName, String searchByLastName) {
    return userRepository.count(searchByFirstName, searchByLastName);
  }

  @Override
  public List<UserDto> getAll(Integer offset, Integer max, String searchByFirstName, String searchByLastName) {
    log.info("Find all Users, Offset: " + offset + ", Max: " + max, ", Filtered by: firstName: " + searchByFirstName + ", LastName: " + searchByLastName);
    return mapTo(
        userRepository.findAll(offset, max, searchByFirstName, searchByLastName)
        ); 
  }

  /*
   * (non-Javadoc)
   * @see ${package}.service.UserService\#generateLogin(java.lang.String, java.lang.String)
   */
  @Override
  public String generateLogin(String firstName, String lastName) {
    log.info("Generate an user login, firstName: " + firstName + ", lastName: " + lastName);
    String login = null;
    Boolean finished = false;

    if (firstName == null || lastName == null)
      return login;

    Integer searchPosition = 1;
    while (!finished) {
      if (searchPosition > firstName.length()) {
        login = null;
        finished = true;
        continue;
      }

      login = (firstName.replaceAll("\\s", "").substring(0, searchPosition) + lastName.replaceAll("\\s", "")).toLowerCase();
      log.info("Proposed login: " + login);
      if (userRepository.findByLogin(login) == null)
        finished = true;

      searchPosition++;
    }
    return login;
  }

  /*
   * (non-Javadoc)
   * @see ${package}.service.UserService\#getByEmployeeNumber(java.lang.String)
   */
  @Override
  public UserDto getByEmployeeNumber(String employeeNumber) {
    log.info("Find User by employeeNumber: " + employeeNumber);
    UserDto retval = null;
    User user = userRepository.findByEmployeeNumber(employeeNumber);

    if (user != null)
      retval = mapTo(userRepository.findByEmployeeNumber(employeeNumber));

    return retval;
  }

}
