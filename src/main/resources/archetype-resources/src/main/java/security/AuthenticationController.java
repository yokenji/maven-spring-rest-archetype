package ${package}.security;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
@RestController
public class AuthenticationController {

  private TokenAuthenticationService tokenAuthenticationService;
  private AuthenticationManager authenticationManager;

  @Autowired
  public AuthenticationController(AuthenticationManager authenticationManager, TokenAuthenticationService tokenAuthenticationService) {
    this.authenticationManager = authenticationManager;
    this.tokenAuthenticationService = tokenAuthenticationService;
  }

  @RequestMapping(value = "/v1/auth/sign-in", method = RequestMethod.POST)
  public ResponseEntity<Object> signIn(@Valid JwtAuthenticationRequest authRequest, BindingResult result) {
    UsernamePasswordAuthenticationToken authToken = 
        new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());

    Authentication authentication = authenticationManager.authenticate(authToken);
    SecurityContextHolder.getContext().setAuthentication(authToken);
    String token = tokenAuthenticationService.addAuthentication(authentication);
    return ResponseEntity.ok(new JwtAuthenticationResponse(token));
  }

}