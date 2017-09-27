package ${package}.security;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
@Service
public class TokenAuthenticationService {

  private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";

  private static final Logger logger = LogManager.getLogger(TokenAuthenticationService.class);

  private final TokenHandler tokenHandler;

  @Autowired
  public TokenAuthenticationService(TokenHandler tokenHandler) {
    this.tokenHandler = tokenHandler;
  }

  public String addAuthentication(Authentication authentication) {
    final UserDetails user = (UserDetails) authentication.getPrincipal();
    logger.debug("Generate an authentication token for user: " + user.getUsername());
    return tokenHandler.generateToken(user); 
  }

  public Authentication getAuthentication(HttpServletRequest request) {
    final String token = request.getHeader(AUTH_HEADER_NAME);
    logger.debug("Authenticate token: " + token);
    if (token != null) {
      CustomUserDetails user = tokenHandler.parseToken(token.replace(AUTH_HEADER_NAME, ""));
      if (user != null) {
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
      }
    }
    return null;
  }

}
