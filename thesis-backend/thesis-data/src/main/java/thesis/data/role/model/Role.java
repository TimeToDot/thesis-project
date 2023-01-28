package thesis.data.role.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import thesis.data.account.model.Account;
import thesis.data.account.model.AccountRole;
import thesis.data.permission.model.Privilege;

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
@Table(name = "role")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private RoleType name;

  @ManyToMany
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinTable(
          name = "role_privilege",
          joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id")
  )
  @ToString.Exclude
  private List<Privilege> privileges;

/*  @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)*/
  @OneToMany(mappedBy = "account")
  @ToString.Exclude
  private List<AccountRole> accountRoles;

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