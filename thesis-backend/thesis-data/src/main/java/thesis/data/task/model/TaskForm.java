package thesis.data.task.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import thesis.data.project.model.Project;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Entity
@Table(name = "task_form")
public class TaskForm {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @OneToOne(mappedBy = "taskForm", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @ToString.Exclude
  private TaskFormDetails details;

  private String name;

  private String description;

  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @Temporal(TemporalType.TIMESTAMP)
  private Date archiveDate;

  @ManyToOne
  @JoinColumn(name = "id_project")
  private Project project;

}