package ${package}.dto;

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
  private Boolean isActive;

  private List<String> roles;

  @NotNull
  @Pattern(regexp = "[A-Z]{2}")
  private String language;

  /**
   * Default constructor.
   */
  public UserDto() {
    this.isActive = false;
  }

  /**
   * Default constructor.
   * 
   * @param Long id
   * @param String firstName
   * @param String lastName
   * @param String login
   * @param String password
   * @param Boolean isActive
   * @param List<String> roles
   * @param String language
   * @param String email;
   */
  public UserDto(Long id, String firstName, String lastName, String login, 
      String password, Boolean isActive, List<String> roles, String language, String email) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.login = login;
    this.password = password;
    this.isActive = isActive;
    this.roles = roles;
    this.language = language;
    this.email = email;
  }

  /**
   * Get the id
   *
   * @return Long
   */
  @Override
  public Long getId() {
    return id;
  }

  /**
   * Set the id 
   *
   * @param Long id
   */
  @Override
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
   * Get the isActive
   *
   * @return Boolean
   */
  public Boolean getIsActive() {
    return isActive;
  }

  /**
   * Set the isActive 
   *
   * @param Boolean isActive
   */
  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

  /**
   * Get the roles
   *
   * @return List<String>
   */
  public List<String> getRoles() {
    return roles;
  }

  /**
   * Set the roles 
   *
   * @param List<String> roles
   */
  public void setRoles(List<String> roles) {
    this.roles = roles;
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
