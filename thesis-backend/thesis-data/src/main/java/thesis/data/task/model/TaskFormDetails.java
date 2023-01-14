package thesis.data.task.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import thesis.data.project.model.Project;

import javax.persistence.*;
import java.util.UUID;

@Getter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Entity
@Table(name = "task_form_details")
public class TaskFormDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @OneToOne
  @JoinColumn(name = "task_form_id")
  private TaskForm taskForm;

  private String description;

  @Enumerated(EnumType.STRING)
  private TaskFormType status;
}