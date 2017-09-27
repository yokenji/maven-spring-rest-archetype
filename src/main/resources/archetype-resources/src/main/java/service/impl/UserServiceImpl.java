package ${package}.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mattheeuws.security.dto.ProfileDto;
import com.mattheeuws.security.dto.UserDto;
import com.mattheeuws.security.exception.NotFoundException;
import com.mattheeuws.security.model.Role;
import com.mattheeuws.security.model.User;
import com.mattheeuws.security.model.type.UserLanguage;
import com.mattheeuws.security.repository.UserRepository;
import com.mattheeuws.security.security.SpringSecurityService;
import com.mattheeuws.security.service.UserService;
import com.mattheeuws.security.util.impl.StringHelper;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

  private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

  private UserRepository userRepository;
  private SpringSecurityService securityService;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, SpringSecurityService securityService) {
    this.userRepository = userRepository;
    this.securityService = securityService;
  }

  /* (non-Javadoc)
   * @see com.mattheeuws.security.service.BaseService\#save(java.lang.Object)
   */
  @Override
  public UserDto save(UserDto dto) {
    return mapTo(userRepository.save(
        mapTo(dto)));
  }

  /* (non-Javadoc)
   * @see com.mattheeuws.security.service.BaseService\#getById(java.lang.Long)
   */
  @Override
  public UserDto getById(Long id) {
    logger.debug("Find User by Id: " + id);
    User user = userRepository.findById(id);
    if (user == null)
      throw new NotFoundException("User with id: " + id + " not found!");
    return mapTo(user);
  }

  /* (non-Javadoc)
   * @see com.mattheeuws.security.service.BaseService\#getAll()
   */
  @Override
  public List<UserDto> getAll() {
    logger.debug("Find all users.");
    return mapTo(
        userRepository.findAll()
        );
  }

  /* (non-Javadoc)
   * @see com.mattheeuws.security.service.BaseService\#getAll(java.lang.Integer, java.lang.Integer, java.lang.String)
   */
  @Override
  public List<UserDto> getAll(Integer offset, Integer max) {
    logger.debug("Find all Users starting from: " + offset + " until: " + max);
    return mapTo(
        userRepository.findAll(offset, max)); 
  }

  /*
   * (non-Javadoc)
   * @see com.mattheeuws.security.service.BaseService\#count()
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
    logger.info("Map an UserDto to an User entity.");
    User user = null;
    if (userDto.getId() != null) {
      user = userRepository.findById(userDto.getId());
      if (user == null)
        throw new NotFoundException("User with id: " + userDto.getId() + " not found");
    } else {
        user = new User();
    }

    if (userDto.getPassword() != null && !userDto.getPassword().isEmpty())
      user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));

    user.setFirstName(userDto.getFirstName());
    user.setLastName(userDto.getLastName());
    user.setEmail(userDto.getEmail());
    user.setLogin(userDto.getLogin());
    user.setIsActive(userDto.getIsActive());
    
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
    logger.debug("Map an User entity to an UserDto.");
    UserDto dto = new UserDto();
    dto.setId(user.getId());
    dto.setDateCreated(user.getDateCreated());
    dto.setCreatedBy(user.getCreatedBy());
    dto.setLastUpdated(user.getLastUpdated());
    dto.setUpdatedBy(user.getUpdatedBy());
    dto.setFirstName(user.getFirstName());
    dto.setLastName(user.getLastName());
    dto.setLogin(user.getLogin());
    
    dto.setIsActive(user.getIsActive());
    dto.setRoles(null);
    dto.setLanguage(user.getUserLanguage().name());
    dto.setEmail(user.getEmail());
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
   * @see com.mattheeuws.security.service.UserService\#getUserLanguages()
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
   * @see com.mattheeuws.security.service.UserService\#isLoginUnique(java.lang.String, java.lang.Long)
   */
  @Override
  public Boolean isLoginUnique(String login, Long id) {
    return userRepository.isLoginUnique(login, id);
  }

  /*
   * (non-Javadoc)
   * @see com.mattheeuws.security.service.UserService\#getByLogin(java.lang.String)
   */
  @Override
  public UserDto getByLogin(String login) {
    return 
        mapTo(userRepository.findByLogin(login));
  }

  /*
   * (non-Javadoc)
   * @see com.mattheeuws.security.service.UserService\#count(java.lang.String, java.lang.String)
   */
  @Override
  public Long count(String searchByFirstName, String searchByLastName) {
    return userRepository.count(searchByFirstName, searchByLastName);
  }

  @Override
  public List<UserDto> getAll(Integer offset, Integer max, String searchByFirstName, String searchByLastName) {
    logger.info("Find all Users, Offset: " + offset + ", Max: " + max, ", Filtered by: firstName: " + searchByFirstName + ", LastName: " + searchByLastName);
    return mapTo(
        userRepository.findAll(offset, max, searchByFirstName, searchByLastName)
        ); 
  }

  /*
   * (non-Javadoc)
   * @see com.mattheeuws.security.service.UserService\#generateLogin(java.lang.String, java.lang.String)
   */
  @Override
  public String generateLogin(String firstName, String lastName) {
    logger.info("Generate an user login, firstName: " + firstName + ", lastName: " + lastName);
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
      logger.info("Proposed login: " + login);
      if (userRepository.findByLogin(login) == null)
        finished = true;

      searchPosition++;
    }
    return login;
  }

  @Override
  public ProfileDto getUserProfile() {
    User currentUser = securityService.getCurrentUser();
    return new ProfileDto(
        currentUser.getFirstName(),
        currentUser.getLastName(),
        currentUser.getLogin(),
        StringHelper.byteArrayToBase64String(currentUser.getPhoto()),
        convertRolesTo(currentUser.getRoles())
        );
  }

  /**
   * @param Collection<Role> roles
   * 
   * @return List<String>
   */
  private List<String> convertRolesTo(Collection<Role> roles) {
    return roles.stream()
        .map(x -> x.getName())
        .collect(Collectors.toList());
  }

}
