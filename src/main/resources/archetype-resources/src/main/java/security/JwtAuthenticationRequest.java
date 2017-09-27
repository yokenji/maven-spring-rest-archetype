package ${package}.security;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 * 
 */
public class JwtAuthenticationRequest {

  private String username;
  private String password;

  public JwtAuthenticationRequest() {
    super();
  }
  
  public JwtAuthenticationRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }

  /**
   * Get the username
   *
   * @return String
   */
  public String getUsername() {
    return username;
  }

  /**
   * Set the username 
   *
   * @param String username
   */
  public void setUsername(String username) {
    this.username = username;
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

}
