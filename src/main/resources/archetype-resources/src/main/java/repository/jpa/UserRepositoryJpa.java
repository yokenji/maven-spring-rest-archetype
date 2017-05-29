package ${package}.repository.jpa;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ${package}.model.User;
import ${package}.repository.UserRepository;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
@Repository
public class UserRepositoryJpa implements UserRepository {

  @PersistenceContext
  private EntityManager em;

  /* (non-Javadoc)
   * @see ${package}.repository.BaseRepository\#save(java.lang.Object)
   */
  @Override
  public User save(User entity) {
    return em.merge(entity);
  }

  /* (non-Javadoc)
   * @see ${package}.repository.BaseRepository\#findAll()
   */
  @Override
  public List<User> findAll() {
    String queryString = "from User u";
    List<User> users = em.createQuery(queryString, User.class)
        .getResultList();
    return users;
  }

  /* (non-Javadoc)
   * @see ${package}.repository.BaseRepository\#findById(java.lang.Long)9
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
   * @see ${package}.repository.UserRepository\#findByLogin(java.lang.String)
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
    List<User> results = em.createQuery(queryString, User.class)
        .setFirstResult(offset)
        .setMaxResults(max)
        .getResultList();
    return results;
  }

  @Override
  public Long count() {
    Long count = 0L;
    String querySring = "select count(u.id) from User u";
    TypedQuery<Long> results = em.createQuery(querySring, Long.class);
    try {
      count = results.getSingleResult();
    } catch (NoResultException e) {}
    return count;
  }

  /*
   * (non-Javadoc)
   * @see ${package}.repository.UserRepository\#isLoginUnique(java.lang.String, java.lang.Long)
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
   * @see ${package}.repository.UserRepository\#count(java.lang.String, java.lang.String)
   */
  @Override
  public Long count(String searchByFirstName, String searchByLastName) {
    Long count = 0L;
    String querySring = "select count(u.id) from User u " +
        " where (:byFirstName IS NULL OR u.firstName like :byFirstName) " + 
        " and (:byLastName IS NULL OR u.lastName like :byLastName)";
    TypedQuery<Long> results = em.createQuery(querySring, Long.class)
        .setParameter("byFirstName", searchByFirstName + "%")
        .setParameter("byLastName", searchByLastName + "%");
    try {
      count = results.getSingleResult();
    } catch (NoResultException e) {}
    return count;
  }

  /*
   * (non-Javadoc)
   * @see ${package}.repository.UserRepository\#findAll(int, int, java.lang.String, java.lang.String)
   */
  @Override
  public List<User> findAll(int offset, int max, String searchByFirstName, String searchByLastName) {
    String queryString = "from User u " +
        " where (:byFirstName IS NULL OR u.firstName like :byFirstName) " +
        " and (:byLastName IS NULL OR u.lastName like :byLastName)";
    List<User> results = em.createQuery(queryString, User.class)
        .setParameter("byFirstName", searchByFirstName + "%")
        .setParameter("byLastName", searchByLastName + "%")
        .setFirstResult(offset)
        .setMaxResults(max)
        .getResultList();
    return results;
  }

  /*
   * (non-Javadoc)
   * @see ${package}.repository.UserRepository\#findByEmployeeNumber(java.lang.String)
   */
  @Override
  public User findByEmployeeNumber(String employeeNumber) {
    String queryString = "from User u " +
        " where u.employeeNumber = :employeeNumber ";

    List<User> results = em.createQuery(queryString, User.class)
        .setParameter("employeeNumber", employeeNumber)
        .getResultList();

    return (!results.isEmpty() ? results.get(0) : null);
  }

}
