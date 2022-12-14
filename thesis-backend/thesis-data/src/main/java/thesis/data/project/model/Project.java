package thesis.data.project.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import thesis.data.account.model.Account;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Entity
@Table(name = "project")
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private String name;

  private String description;

  @OneToOne
  @JoinColumn(name = "owner_id")
  private Account owner;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  @ToString.Exclude
  private ProjectDetails details;

  @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
  @ToString.Exclude
  private List<ProjectAccountRole> projectAccountRoles;

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