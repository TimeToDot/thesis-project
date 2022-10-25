package thesis.data.task.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import thesis.data.project.model.Project;

import javax.persistence.*;
import java.util.UUID;

@Getter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "task_form")
public class TaskForm {

  @Id
  private UUID id;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  @ToString.Exclude
  private TaskFormDetails details;

  private String name;

  private String description;

  @ManyToOne
  @JoinColumn(name = "id_project")
  private Project project;

  public void setDetails(TaskFormDetails details) {
    this.details = details;
  }
}