package thesis.data.task.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Getter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "task_form_details")
public class TaskFormDetails {

  @Id
  private UUID id;

  private UUID taskFormId;

  private String description;

  @Enumerated(EnumType.STRING)
  private TaskType type;
}