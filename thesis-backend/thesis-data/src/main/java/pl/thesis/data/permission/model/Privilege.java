package pl.thesis.data.permission.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.thesis.data.role.model.Role;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "privilege")
public class Privilege {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Enumerated(EnumType.STRING)
  private PrivilegeType name;

  @ManyToMany(mappedBy = "privileges", fetch = FetchType.LAZY)
  @ToString.Exclude
  private List<Role> roles;

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
      return false;
    Privilege privilege = (Privilege) o;
    return id != null && Objects.equals(id, privilege.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}