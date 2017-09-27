package ${package}.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.mattheeuws.security.model.User;
import com.mattheeuws.security.repository.UserRepository;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
@Repository
public class UserRepositoryJpa implements UserRepository {

  @PersistenceContext
  private EntityManager em;

  /* (non-Javadoc)
   * @see com.mattheeuws.security.repository.BaseRepository\#save(java.lang.Object)
   */
  @Override
  public User save(User entity) {
    return em.merge(entity);
  }

  /* (non-Javadoc)
   * @see com.mattheeuws.security.repository.BaseRepository\#findAll()
   */
  @Override
  public List<User> findAll() {
    String queryString = "from User u";
    return em.createQuery(queryString, User.class)
        .getResultList();
  }

  /* (non-Javadoc)
   * @see com.mattheeuws.security.repository.BaseRepository\#findById(java.lang.Long)9
   */
  @Override
  public User findById(Long id) {
    String queryString = "from User u " + 
    " where u.id = :id";
    List<User> users = em.createQuery(queryString, User.class)
        .setParameter("id", id)
        .getResultList();
    return (!users.isEmpty() ? users.get(0) : null);
  }

  /* (non-Javadoc)
   * @see com.mattheeuws.security.repository.UserRepository\#findByLogin(java.lang.String)
   */
  @Override
  public User findByLogin(String login) {
    String queryString = "from User u where u.login = :login";
    List<User> users = em.createQuery(queryString, User.class)
        .setParameter("login", login)
        .getResultList();
    return (!users.isEmpty() ? users.get(0) : null);
  }

  @Override
  public List<User> findAll(int offset, int max) {
    String queryString = "from User u";
    return em.createQuery(queryString, User.class)
        .setFirstResult(offset)
        .setMaxResults(max)
        .getResultList();
  }

  @Override
  public Long count() {
    return Long.valueOf(
        findAll().size());
  }

  /*
   * (non-Javadoc)
   * @see com.mattheeuws.security.repository.UserRepository\#isLoginUnique(java.lang.String, java.lang.Long)
   */
  @Override
  public Boolean isLoginUnique(String login, Long id) {
    String queryString = "from User u where u.login= :login and u.id != :id";
    List<User> results = em.createQuery(queryString, User.class)
        .setParameter("login", login)
        .setParameter("id", (id != null ? id : 0))
        .getResultList();
    return results.isEmpty();
  }

  /*
   * (non-Javadoc)
   * @see com.mattheeuws.security.repository.UserRepository\#count(java.lang.String, java.lang.String)
   */
  @Override
  public Long count(String searchByFirstName, String searchByLastName) {
    return Long.valueOf(
        findAll(-1, -1, searchByFirstName, searchByLastName).size());
  }

  /*
   * (non-Javadoc)
   * @see com.mattheeuws.security.repository.UserRepository\#findAll(int, int, java.lang.String, java.lang.String)
   */
  @Override
  public List<User> findAll(int offset, int max, String searchByFirstName, String searchByLastName) {
    String queryString = "from User u " +
        " where (:byFirstName IS NULL OR u.firstName like :byFirstName) " +
        " and (:byLastName IS NULL OR u.lastName like :byLastName)";

    TypedQuery<User> query = em.createQuery(queryString, User.class)
        .setParameter("byFirstName", searchByFirstName + "%")
        .setParameter("byLastName", searchByLastName + "%");

    if (offset > -1 && max > -1) {
      query.setFirstResult(offset);
      query.setMaxResults(max);
    }

    return query.getResultList();
  }

}
