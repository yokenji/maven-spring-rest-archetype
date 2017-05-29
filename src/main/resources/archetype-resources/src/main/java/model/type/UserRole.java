package ${package}.model.type;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
public enum UserRole {

  ROLE_ADMIN,
  ROLE_USER;

  /**
   * Search for the userRole by the given value, default is ROLE_USER.
   * 
   * @param String value
   * @return UserRole
   */
  public static UserRole userRole(String value) {
    UserRole retval = UserRole.ROLE_USER;
    for (UserRole role : UserRole.values()) {
      if (role.name().toLowerCase().equals(value.toLowerCase())) {
        retval = role;
        break;
      }
    }
    return retval;
  }

  /**
   * Return the role hierarchy.
   * 
   * Define who inherits who.
   * @return String
   */
  public static String roleHierarchy() {
    String retval = "";
    retval += UserRole.ROLE_ADMIN + " > "  + UserRole.ROLE_USER;
    retval += " ";
    retval += UserRole.ROLE_USER;
    return retval;
  }

}
