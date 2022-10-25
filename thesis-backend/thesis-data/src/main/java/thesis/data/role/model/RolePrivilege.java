package thesis.data.role.model;

import lombok.*;
import org.hibernate.Hibernate;
import thesis.data.permission.model.Privilege;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Getter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "role_privilege")
public class RolePrivilege {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "role_id")
  @ToString.Exclude
  private Role role;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "privilege_id")
  @ToString.Exclude
  private Privilege privilege;

  public void setPrivilege(Privilege privilege) {
    this.privilege = privilege;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
      return false;
    RolePrivilege that = (RolePrivilege) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}