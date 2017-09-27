package ${package}.dto;

import java.util.List;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
public class ProfileDto {

  private String firstName;
  private String lastName;
  private String userName;
  private String base64Photo;
  private List<String> roles;

  public ProfileDto() {
    super();
  }

  /**
   * 
   * @param firstName
   * @param lastName
   * @param base64Photo
   * @param roles
   */
  public ProfileDto(String firstName, String lastName, String userName, String base64Photo, List<String> roles) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
    this.base64Photo = base64Photo;
    this.roles = roles;
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
   * Get the userName
   *
   * @return String
   */
  public String getUserName() {
    return userName;
  }

  /**
   * Set the userName 
   *
   * @param String userName
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * Get the base64Photo
   *
   * @return String
   */
  public String getBase64Photo() {
    return base64Photo;
  }

  /**
   * Set the base64Photo 
   *
   * @param String base64Photo
   */
  public void setBase64Photo(String base64Photo) {
    this.base64Photo = base64Photo;
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

}
