package thesis.data.account.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.UUID;

@Getter
@SuperBuilder
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "account_details")
public class AccountDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private UUID id;

  private UUID accountId;

  @NotBlank(message="name is required")
  private String name;

  @NotBlank(message="surname is required")
  private String surname;

  @NotBlank(message="pesel is required")
  private String pesel;

  @NotBlank(message="phonenumber is required")
  private String phoneNumber;

  @NotBlank(message="street is required")
  private String street;

  @NotBlank(message="city is required")
  private String city;

  @NotBlank(message="create timestamp  is required")
  private Long createdAt;

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
      return false;
    AccountDetails that = (AccountDetails) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}