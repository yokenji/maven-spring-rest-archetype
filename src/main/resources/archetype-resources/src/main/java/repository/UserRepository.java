package ${package}.repository;

import java.util.List;

import com.mattheeuws.security.model.User;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
public interface UserRepository extends BaseRepository<User> {

  /**
   * Find User by it's login.
   * 
   * @param String login
   * @return User
   */
  public User findByLogin(String login);

  /**
   * Check if the login is unique.
   * 
   * @param String login
   * @param id - the record that must be skipped.
   * @return Boolean
   */
  public Boolean isLoginUnique(String login, Long id);

  /**
   * Find the numbers of DTO's filtered by.
   * 
   * @param String searchByFirstName
   * @param String searchByLastName
   * @return Long
   */
  public Long count(String searchByFirstName, String searchByLastName);

  /**
   * Return all entities with pagination filtered by.
   * 
   * @param int offset
   * @param int max
   * @param String searchByFirstName
   * @param String searchByLastName
   * @return List<User>
   */
  public List<User> findAll(int offset, int max, String searchByFirstName, String searchByLastName);

}
