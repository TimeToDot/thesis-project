package thesis.data.account.model;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "account_details")
public class AccountDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long accountId;

  private String name;

  private String surname;

  private String pesel;

  private String email;

  private OffsetDateTime createdAt;

}