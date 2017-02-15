package ${package}.conf;

import java.util.List;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
public interface BaseRepository<T> {

  /**
   * Save new or changements.
   * 
   * @param T entity
   * @return T
   */
  public T save(T entity);

  /**
   * Return all entities.
   * 
   * @return List<T>
   */
  public List<T> findAll();

  /**
   * Find entity by id.
   * 
   * @param Long id
   * @return T
   */
  public T findById(Long id);

}
