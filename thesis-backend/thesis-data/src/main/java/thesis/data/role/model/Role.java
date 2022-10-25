package thesis.data.role.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;
import thesis.data.account.model.AccountRole;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "role")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private UUID id;

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private RoleType name;

  @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
  @ToString.Exclude
  private List<RolePrivilege> rolePrivilegeList;

  @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
  @ToString.Exclude
  private List<AccountRole> accountList;

  public void setAccountList(List<AccountRole> accountList) {
    this.accountList = accountList;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
      return false;
    Role role = (Role) o;
    return id != null && Objects.equals(id, role.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}