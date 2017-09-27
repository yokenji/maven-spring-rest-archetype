package ${package}.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${package}.dto.RoleDto;
import ${package}.exception.NotFoundException;
import ${package}.model.Role;
import ${package}.repository.RoleRepository;
import ${package}.service.RoleService;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

  private static final Logger logger = LogManager.getLogger(RoleServiceImpl.class);

  private RoleRepository roleRepository;

  @Autowired
  public RoleServiceImpl(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public RoleDto save(RoleDto dto) {
    return mapTo(roleRepository.save(
        mapTo(dto)));
  }

  @Override
  public RoleDto getById(Long id) {
    logger.debug("Find Role by id: " + id);
    Role role = roleRepository.findById(id);
    if (role == null)
      throw new NotFoundException("Role with id " + id + " not found!");
    return mapTo(role);
  }

  @Override
  public List<RoleDto> getAll() {
    return mapTo(
        roleRepository.findAll());
  }

  @Override
  public List<RoleDto> getAll(Integer offset, Integer max) {
    logger.debug("Find all starting from: " + offset + " until: " + max);
    return mapTo(
        roleRepository.findAll(offset, max));
  }

  @Override
  public Long count() {
    return roleRepository.count();
  }

  /**
   * Map an entity to a dto.
   * 
   * @param Role role
   * @return RoleDto
   */
  private RoleDto mapTo(Role role) {
    logger.debug("Map an Role entity to a Role dto.");
    RoleDto dto = new RoleDto(role.getName());
    dto.setId(role.getId());
    dto.setDateCreated(role.getDateCreated());
    dto.setCreatedBy(role.getCreatedBy());
    dto.setLastUpdated(role.getLastUpdated());
    dto.setUpdatedBy(role.getUpdatedBy());
    return dto;
  }

  /**
   * Map entities to dtos.
   * 
   * @param List<Role> roles
   * @return List<RoleDto>
   */
  private List<RoleDto> mapTo(List<Role> roles) {
    List<RoleDto> dtos = new ArrayList<>();
    if (roles == null)
      return dtos;
    for (Role role : roles) {
      dtos.add(mapTo(role));
    }
    return dtos;
  }

  /**
   * Map a dto to an entity.
   * 
   * @param RolDto roleDto
   * @return Role
   */
  private Role mapTo(RoleDto roleDto) {
    logger.debug("Map an Entity to a dto.");
    Role role = null;
    if (roleDto.getId() != null) {
      role = roleRepository.findById(roleDto.getId());
      if (role == null)
        throw new NotFoundException("Role with id " + roleDto.getId() + " not found!");
    } else {
        role = new Role();
    }

    role.setName(roleDto.getName());

    return role;
  }

  @Override
  public Boolean isNameUnique(String name, Long id) {
    return roleRepository.isNameUnique(name, id);
  }
}
