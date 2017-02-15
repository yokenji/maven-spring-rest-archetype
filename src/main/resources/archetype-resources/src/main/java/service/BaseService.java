package ${package}.conf;

import java.util.List;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
public interface BaseService<T> {

  /**
   * Save/Update a DTO.
   * 
   * @param T dto
   * @return T
   */
  public T save (T dto);

  /**
   * Return DTO by Id.
   * 
   * @param id
   * @return T
   */
  public T getById(Long id);

  /**
   * Return all DTO's
   * 
   * @return List<T>
   */
  public List<T> getAll();

  /**
   * Return all DTO's for pagination filtered by.
   * 
   * @param Integer offset
   * @param Integer max
   * @param String searchFilter
   * @return List<T>
   */
  public List<T> getAll(Integer offset, Integer max, String searchFilter);

  /**
   * Return the numbers of DTO's filtered by.
   * 
   * @param String searchFilter
   * @return Long
   */
  public Long count(String searchFilter);
}
