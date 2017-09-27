package ${package}.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class RoleDto extends BaseDto {

  @NotNull
  @Pattern(regexp = "ROLE_([A-Z0-9_]*)")
  private String name;

  public RoleDto() {
    super();
  }

  public RoleDto(String name) {
    this.name = name;
  }

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

}
