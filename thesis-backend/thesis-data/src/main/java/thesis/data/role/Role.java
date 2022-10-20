package thesis.data.role;

import lombok.Data;
import thesis.data.account.model.AccountRole;
import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "role")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private RoleType name;

  @OneToMany(mappedBy = "role")
  private List<AccountRole> accountRoleList;
}