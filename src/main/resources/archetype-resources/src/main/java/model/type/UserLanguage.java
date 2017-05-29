package ${package}.model.type;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
public enum UserLanguage {

  NL,
  EN,
  FR;

  /**
   * Search for the userLanguage by the given value, default is EN.
   * 
   * @param String value
   * @return UserLanguage
   */
  public static UserLanguage userLanguage(String value) {
    UserLanguage retval = UserLanguage.EN;
    for (UserLanguage language : UserLanguage.values()) {
      if (language.name().toLowerCase().equals(value.toLowerCase())) {
        retval = language;
        break;
      }
    }
    return retval;
  }
}