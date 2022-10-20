package thesis.data.role;

import lombok.Data;
import thesis.data.permission.Privilege;
import thesis.data.role.Role;

import javax.persistence.*;

@Data
@Entity
@Table(name = "roles_privileges")
public class RolePrivilege {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "role_id")
  private Role role;

  @ManyToOne
  @JoinColumn(name = "privilege_id")
  private Privilege privilege;

}