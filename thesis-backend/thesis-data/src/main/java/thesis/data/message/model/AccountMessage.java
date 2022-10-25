package thesis.data.message.model;

import lombok.*;
import org.hibernate.Hibernate;
import thesis.data.account.model.Account;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Getter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "account_message")
public class AccountMessage {

  @Id
  private UUID id;

  private String data;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_from")
  @ToString.Exclude
  private Account accountFrom;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_to")
  @ToString.Exclude
  private Account accountTo;

  private Long timestamp;

  public void setAccountTo(Account accountTo) {
    this.accountTo = accountTo;
  }

  public void setAccountFrom(Account accountFrom) {
    this.accountFrom = accountFrom;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
      return false;
    AccountMessage that = (AccountMessage) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}