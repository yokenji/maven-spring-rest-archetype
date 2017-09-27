package ${package}.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ${package}.model.User;
import ${package}.repository.UserRepository;


/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
@Service
public class CustomUserDetailsService implements UserDetailsService{

  private static final Logger log = LogManager.getLogger(CustomUserDetailsService.class);

  private UserRepository userRepository;

  @Autowired
  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public CustomUserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
    log.info("Authenticating Login: " + login);

    User user = userRepository.findByLogin(login);
    if (user == null) {
      log.error("User " +  login + " not found!");
      throw new UsernameNotFoundException("User " + login + " not found!");
    }

    return new CustomUserDetails(
        user.getLogin(),
        user.getPassword(),
        user.getIsActive(),
        true,
        true,
        true,
        user.getAuthorities());

  }
}
