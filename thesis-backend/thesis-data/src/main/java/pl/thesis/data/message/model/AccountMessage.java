package pl.thesis.data.message.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.thesis.data.account.model.Account;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Entity
@Table(name = "account_message")
public class AccountMessage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String data;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_from")
  @ToString.Exclude
  private Account accountFrom;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_to")
  @ToString.Exclude
  private Account accountTo;

  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;


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