package ${package}.service;

import ${package}.dto.RoleDto;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
public interface RoleService extends BaseService<RoleDto> {

  /**
   * 
   * @param String login
   * @param Long id - the record that must be skipped
   * @return Boolean
   */
  public Boolean isNameUnique(String name, Long id);
}
