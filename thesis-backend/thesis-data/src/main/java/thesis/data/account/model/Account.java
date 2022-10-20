package thesis.data.account.model;

import lombok.Data;
import thesis.data.role.Role;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "account", uniqueConstraints = {
        @UniqueConstraint(columnNames = "login")
        })
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String login;

  private String pass;

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private StatusType status;

  @OneToMany(mappedBy = "account")
  private List<AccountRole> accountRoleList;
}