package ${package}.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.mattheeuws.security.model.Role;
import com.mattheeuws.security.repository.RoleRepository;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
@Repository
public class RoleRepositoryJpa implements RoleRepository {

  @PersistenceContext
  private EntityManager em;

  @Override
  public Role save(Role entity) {
    return em.merge(entity);
  }

  @Override
  public List<Role> findAll() {
    String queryString = "from Role r";
    return em.createQuery(queryString, Role.class)
        .getResultList();
  }

  @Override
  public Role findById(Long id) {
    String queryString = "from Role r where r.id = :id";
    List<Role> results = em.createQuery(queryString, Role.class)
        .setParameter("id", id)
        .getResultList();
    return (!results.isEmpty() ? results.get(0) : null);
  }

  @Override
  public List<Role> findAll(int offset, int max) {
    String queryString = "from Role r";
    return em.createQuery(queryString, Role.class)
        .setFirstResult(offset)
        .setMaxResults(max)
        .getResultList();
  }

  @Override
  public Long count() {
    return Long.valueOf(
        findAll().size());
  }

  @Override
  public Boolean isNameUnique(String name, Long id) {
    String queryString = "from Role r where r.name = :name and r.id != :id";
    return em.createQuery(queryString, Role.class)
        .setParameter("name", name)
        .setParameter("id", (id != null ? id : 0))
        .getResultList()
        .isEmpty();
  }
}
