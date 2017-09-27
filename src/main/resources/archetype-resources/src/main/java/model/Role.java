package ${package}.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
@Entity
@Table(name = "role")
public class Role extends BaseEntity {

  private static final long serialVersionUID = 1L;

  @Column(name = "name", unique = true)
  private String name;

  @ManyToMany(mappedBy = "roles")
  private Collection<User> users;

  /**
   * Get the name
   *
   * @return String
   */
  public String getName() {
    return name;
  }

  /**
   * Set the name 
   *
   * @param String name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get the users
   *
   * @return Collection<User>
   */
  public Collection<User> getUsers() {
    return users;
  }

  /**
   * Set the users 
   *
   * @param Collection<User> users
   */
  public void setUsers(Collection<User> users) {
    this.users = users;
  }

}
