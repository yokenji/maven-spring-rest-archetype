package ${package}.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import ${package}.model.User;
import ${package}.repository.UserRepository;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
@Service
public class SpringSecurityService {

  private final Logger log = LogManager.getLogger(SpringSecurityService.class);

  private UserRepository userRepository;
  private UserDetailsService userDetailsService;

  @Autowired
  public SpringSecurityService(UserRepository userRepository, UserDetailsService userDetailsService) {
    this.userRepository = userRepository;
    this.userDetailsService = userDetailsService;
  }

  /**
   * Return the authenticated principal.
   * 
   * @return Authentication
   */
  public Authentication getAuth() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  /**
   * Indicate if the user is authenticated.
   * 
   * @return Boolean
   */
  public Boolean isAuthenticated() {
    Boolean retval = false;

    log.debug("Check if the user is authenticated.");
    Authentication auth = getAuth();
    if (auth != null) {
      retval = auth.isAuthenticated();
    }

    log.debug("User is authenticated: " + retval);
    return retval;
  }

  /**
   * Return the User information of the logged in user.
   * 
   * @return User
   */
  public User getCurrentUser() {
    log.info("Fetch the user object.");
    User retval = null;
    Authentication auth = getAuth();
    if (auth != null) {
      retval = userRepository.findByLogin(auth.getName());
    }
    return retval;
  }

  /**
   * Load a specific user.
   * 
   * @param String login
   */
  public void loadUser(String login) {
    CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(login);
    Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(auth);
  }

  /**
   * Load the systemUser.
   */
  public void loadSystemUser() {
    CustomUserDetails userDetails = createSystemUser();
    Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(auth);
  }

  /**
   * Unload the user.
   */
  public void unloadUser() {
    SecurityContextHolder.clearContext();
  }

  /**
   * Create a systemUser object.
   * 
   * @return CustomUserDetails
   */
  private CustomUserDetails createSystemUser() {
    CustomUserDetails userDetails = new CustomUserDetails(
        "systemUser",
        "$2a$10$NQDurX8mfs/gydl4X56DgO.CoGNOn2jOYAdvV9MpaUw6QuuqX0IfS",
        false,
        true,
        true,
        true,
        getAuthorities(new String[] {"ROLE_CRON_USER"}));

    return userDetails;
  }

  /**
   * Create the authorities.
   * 
   * @param String[] roles
   * @return Collection<? extends GrantedAuthority>
   */
  private Collection<? extends GrantedAuthority> getAuthorities(String[] roles) {
    Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
    for (int counter = 0; counter < roles.length; counter++) {
      authorities.add(new SimpleGrantedAuthority(roles[counter]));
    }
    return authorities;
  }

}
