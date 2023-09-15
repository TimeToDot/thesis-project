package pl.thesis.data.task.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import pl.thesis.data.project.model.Project;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Entity
@Table(name = "task_form")
public class TaskForm {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

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