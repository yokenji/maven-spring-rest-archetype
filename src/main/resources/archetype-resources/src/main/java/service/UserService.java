package ${package}.service;

import java.util.List;

import ${package}.dto.UserDto;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
public interface UserService extends BaseService<UserDto> {

  /**
   * Return the available roles.
   * 
   * @return List<String>
   */
  public List<String> getUserRoles();

  /**
   * Return the available languages.
   * 
   * @return List<String>
   */
  public List<String> getUserLanguages();

  /**
   * Check if the login is unique.
   * 
   * @param String login
   * @param id - the record that must be skipped.
   * @return Boolean
   */
  public Boolean isLoginUnique(String login, Long id);

  /**
   * Return user by its login.
   * 
   * @param String login
   * @return
   */
  public UserDto getByLogin(String login);

  /**
   * Return the numbers of DTO's filtered by.
   * 
   * @param String searchByFirstName
   * @param String searchByLastName
   * @return Long
   */
  public Long count(String searchByFirstName, String searchByLastName);

  /**
   * Return all DTO's for pagination filtered by.
   * 
   * @param Integer offset
   * @param Integer max
   * @param String searchByFirstName
   * @param String searchByLastName
   * @return List<UserDto>
   */
  public List<UserDto> getAll(Integer offset, Integer max, String searchByFirstName, String searchByLastName);

  /**
   * Generate an user login.
   * 
   * @param String firstName
   * @param String lastName
   * @return String
   */
  public String generateLogin(String firstName, String lastName);

  /**
   * Return user by its employeeNumber.
   * 
   * @param String employeeNumber
   * @return UserDto
   */
  public UserDto getByEmployeeNumber(String employeeNumber);

}
