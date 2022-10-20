package thesis.data.account.model;

import lombok.Data;
import thesis.data.role.Role;

import javax.persistence.*;

@Data
@Entity
@Table(name = "account_roles")
public class AccountRole {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;


  @ManyToOne
  @JoinColumn(name = "role_id")
  private Role role;
}