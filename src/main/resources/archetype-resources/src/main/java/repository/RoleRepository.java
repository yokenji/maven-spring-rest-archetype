package ${package}.repository;

import ${package}.model.Role;

/**
 * @author Delsael Kenji <kenji@delasel.com>, Original Author
 */
public interface RoleRepository extends BaseRepository<Role> {

  /**
   * Check if the name is unique.
   * 
   * @param String name
   * @param Long id - the record that must be skipped
   * @return Boolean
   */
  public Boolean isNameUnique(String name, Long id);
}
