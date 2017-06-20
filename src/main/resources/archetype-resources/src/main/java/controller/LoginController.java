package ${package}.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ${package}.security.SpringSecurityService;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
@Controller
public class LoginController {

  private SpringSecurityService springSecurityService;

  @Autowired
  public LoginController(SpringSecurityService springSecurityService) {
    this.springSecurityService = springSecurityService;
  }

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String loginPage() {
//    if (! (springSecurityService.getAuth() instanceof AnonymousAuthenticationToken)) {
//      // The user is already logged in.
//      return "redirect:/dashboard";
//    }
    return "/logon/login";
  }

  @RequestMapping(value = "/dashboard")
  public String defaultPageAfterLogin() {
    return "dashboard/dashboard";
  }

}