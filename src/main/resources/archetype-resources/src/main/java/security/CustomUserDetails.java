package ${package}.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 *
 */
public class CustomUserDetails extends User {

  private static final long serialVersionUID = 1L;

  public CustomUserDetails(String login, String password,
      boolean enabled,
      boolean accountNonExpired,
      boolean credentialsNonExpired,
      boolean accountNonLocked,
      Collection<? extends GrantedAuthority> authorities) {

    super(login, password, enabled, accountNonExpired, credentialsNonExpired, accountNonExpired, authorities);
  }
}
