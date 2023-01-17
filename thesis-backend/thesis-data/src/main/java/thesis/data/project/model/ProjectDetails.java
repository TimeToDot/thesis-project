package thesis.data.project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Entity
@Table(name = "project_details")
public class ProjectDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  @OneToOne
  @JoinColumn(name = "project_id")
  private Project project;

  private String billingPeriod;

  private Date archiveDate;

  private Integer overtimeModifier;

  private Integer bonusModifier;

  private Integer nightModifier;

  private Integer holidayModifier;

  private String imagePath;

  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  public void setProject(Project project) {
    this.project = project;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
      return false;
    ProjectDetails that = (ProjectDetails) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}