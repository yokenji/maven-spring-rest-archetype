package ${package}.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import ${package}.model.type.UserLanguage;

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

  @Column(name = "login", nullable = false, unique = true, length = 20)
  private String login;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "email")
  private String email;

  @Column(name = "photo", columnDefinition = "BLOB")
  private byte[] photo;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_role",
      joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id"))
  private Collection<Role> roles;

  @Enumerated(EnumType.STRING)
  @Column(name = "language", nullable = false)
  private UserLanguage userLanguage;

  public User() {
    this.isActive = false;
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
   * Get the isActive
   *
   * @return Boolean
   */
  public Boolean getIsActive() {
    return isActive;
  }

  /**
   * Set the isActive 
   *
   * @param Boolean isActive
   */
  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

  /**
   * Get the roles
   *
   * @return Collection<Role>
   */
  public Collection<Role> getRoles() {
    return roles;
  }

  /**
   * Set the roles 
   *
   * @param Collection<Role> roles
   */
  public void setRoles(Collection<Role> roles) {
    this.roles = roles;
  }

  /**
   * 
   * @return Collection<? extends GrantedAuthority>
   */
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Set<GrantedAuthority> authorities = new HashSet<>();
    for (Role role : roles) {
      authorities.add(new SimpleGrantedAuthority(role.getName()));
    }
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

  /**
   * Get the photo
   *
   * @return byte[]
   */
  public byte[] getPhoto() {
    return photo;
  }

  /**
   * Set the photo 
   *
   * @param byte[] photo
   */
  public void setPhoto(byte[] photo) {
    this.photo = photo;
  }

}
