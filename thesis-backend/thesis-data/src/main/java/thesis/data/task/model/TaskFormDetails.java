package thesis.data.task.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

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

  private String description;

  @Enumerated(EnumType.STRING)
  private TaskType type;
}