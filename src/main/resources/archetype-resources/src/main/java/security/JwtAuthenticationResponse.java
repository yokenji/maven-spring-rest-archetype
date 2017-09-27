package ${package}.security;

import java.io.Serializable;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 * 
 * Returns a JWT Authentication.
 */
public final class JwtAuthenticationResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private final String token;

  public JwtAuthenticationResponse(String token) {
    this.token = token;
  }

  public String getToken() {
    return this.token;
  }

}
