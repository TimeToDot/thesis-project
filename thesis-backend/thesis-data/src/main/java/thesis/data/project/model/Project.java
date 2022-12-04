package thesis.data.project.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;
import thesis.data.account.model.Account;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Getter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "project")
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private UUID id;

  private String name;

  private String descroption;

  @OneToOne
  @JoinColumn(name = "account_id")
  private Account account;

  @OneToOne
  @JoinColumn(name = "details_id")
  private ProjectDetails details;

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
      return false;
    Project project = (Project) o;
    return id != null && Objects.equals(id, project.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}