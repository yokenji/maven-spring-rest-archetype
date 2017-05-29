package ${package}.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import ${package}.model.type.UserLanguage;
import ${package}.model.type.UserRole;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
@Entity
@Table(name = "user")
public class User extends BaseEntity {

  private static final long serialVersionUID = 1L;

  @Column(name = "first_name", nullable = false, length = 50)
  private String firstName;

  @Column(name = "last_name", nullable = false, length = 50)
  private String lastName;

  @Column(name = "employee_number", nullable = false)
  private String employeeNumber;

  @Column(name = "login", nullable = false, unique = true, length = 20)
  private String login;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "email")
  private String email;

  @Column(name = "activated", nullable = false)
  private Boolean activated;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private UserRole role;

  @Enumerated(EnumType.STRING)
  @Column(name = "language", nullable = false)
  private UserLanguage userLanguage;

  public User() {
    this.activated = false;
  }

  /**
   * Get the firstName
   *
   * @return String
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Set the firstName 
   *
   * @param String firstName
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Get the lastName
   *
   * @return String
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Set the lastName 
   *
   * @param String lastName
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Get the employeeNumber
   *
   * @return String
   */
  public String getEmployeeNumber() {
    return employeeNumber;
  }

  /**
   * Set the employeeNumber 
   *
   * @param String employeeNumber
   */
  public void setEmployeeNumber(String employeeNumber) {
    this.employeeNumber = employeeNumber;
  }

  /**
   * Get the login
   *
   * @return String
   */
  public String getLogin() {
    return login;
  }

  /**
   * Set the login 
   *
   * @param String login
   */
  public void setLogin(String login) {
    this.login = login;
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

  /**
   * Get the email
   *
   * @return String
   */
  public String getEmail() {
    return email;
  }

  /**
   * Set the email 
   *
   * @param String email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Get the activated
   *
   * @return Boolean
   */
  public Boolean getActivated() {
    return activated;
  }

  /**
   * Set the activated 
   *
   * @param Boolean activated
   */
  public void setActivated(Boolean activated) {
    this.activated = activated;
  }

  /**
   * Get the role
   *
   * @return UserRole
   */
  public UserRole getRole() {
    return role;
  }

  /**
   * Set the role 
   *
   * @param UserRole role
   */
  public void setRole(UserRole role) {
    this.role = role;
  }

  /**
   * A user can have multiple roles but for simplicity we stay with one role.
   * Because spring security expect a collection of roles we do the following.
   * 
   * @return Collection<? extends GrantedAuthority>
   */
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
    authorities.add(new SimpleGrantedAuthority(this.getRole().toString()));
    return authorities;
  }

  /**
   * Get the userLanguage
   *
   * @return UserLanguage
   */
  public UserLanguage getUserLanguage() {
    return userLanguage;
  }

  /**
   * Set the userLanguage 
   *
   * @param UserLanguage userLanguage
   */
  public void setUserLanguage(UserLanguage userLanguage) {
    this.userLanguage = userLanguage;
  }
}
