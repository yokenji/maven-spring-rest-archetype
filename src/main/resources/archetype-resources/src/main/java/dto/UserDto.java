package ${package}.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import ${package}.dto.validator.ValidEmail;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
public class UserDto extends BaseDto{

  @NotNull
  @Size(min = 1, max = 50)
  @Pattern(regexp = "[\\w\\p{L}\\s\\.\\-]*")
  private String firstName;

  @NotNull
  @Size(min = 1, max = 50)
  @Pattern(regexp = "[\\w\\p{L}\\s\\.\\-]*")
  private String lastName;

  @Size(min = 1, max = 19)
  @Pattern(regexp = "[A-Z0-9]+")
  private String employeeNumber;

  @NotNull
  @Size(min = 1, max = 20)
  @Pattern(regexp = "[a-zA-Z0-9]*")
  private String login;

  /**
   * For security reasons the getter of the password will be null.
   * so the input constraints must be put on the UserDtoValidator
   */
  private String password;

  @ValidEmail(required = false, mxLookup = false)
  private String email;

  @NotNull
  private Boolean activated;

  @NotNull
  private String role;

  @NotNull
  @Pattern(regexp = "[A-Z]{2}")
  private String language;

  /**
   * Default constructor.
   */
  public UserDto() {
    this.activated = false;
  }

  /**
   * Default constructor.
   * 
   * @param Long id
   * @param String firstName
   * @param String lastName
   * @param String employeeNumber
   * @param String login
   * @param String password
   * @param Boolean activated
   * @param String language
   * @param String email;
   */
  public UserDto(Long id, String firstName, String lastName, String employeeNumber,
                 String login, String password, Boolean activated, String role, String language, String email) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.employeeNumber = employeeNumber;
    this.login = login;
    this.password = password;
    this.activated = activated;
    this.role = role;
    this.language = language;
    this.email = email;
  }

  /**
   * Get the id
   *
   * @return Long
   */
  public Long getId() {
    return id;
  }

  /**
   * Set the id 
   *
   * @param Long id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Get the firstName
   *
   * @return String
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Set the firstName 
   *
   * @param String firstName
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Get the lastName
   *
   * @return String
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Set the lastName 
   *
   * @param String lastName
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Get the employeeNumber
   *
   * @return String
   */
  public String getEmployeeNumber() {
    return employeeNumber;
  }

  /**
   * Set the employeeNumber 
   *
   * @param String employeeNumber
   */
  public void setEmployeeNumber(String employeeNumber) {
    this.employeeNumber = employeeNumber;
  }

  /**
   * Get the login
   *
   * @return String
   */
  public String getLogin() {
    return login;
  }

  /**
   * Set the login 
   *
   * @param String login
   */
  public void setLogin(String login) {
    this.login = login;
  }

  /**
   * Get the password
   *
   * @return String
   */
  public String getPassword() {
    return password;
  }

  /**
   * Set the password 
   *
   * @param String password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Get the activated
   *
   * @return Boolean
   */
  public Boolean getActivated() {
    return activated;
  }

  /**
   * Set the activated 
   *
   * @param Boolean activated
   */
  public void setActivated(Boolean activated) {
    this.activated = activated;
  }

  /**
   * Get the role
   *
   * @return String
   */
  public String getRole() {
    return role;
  }

  /**
   * Set the role 
   *
   * @param String role
   */
  public void setRole(String role) {
    this.role = role;
  }

  /**
   * Get the language
   *
   * @return String
   */
  public String getLanguage() {
    return language;
  }

  /**
   * Set the language 
   *
   * @param String language
   */
  public void setLanguage(String language) {
    this.language = language;
  }

  /**
   * Get the email
   *
   * @return String
   */
  public String getEmail() {
    return email;
  }

  /**
   * Set the email 
   *
   * @param String email
   */
  public void setEmail(String email) {
    this.email = email;
  }
}
