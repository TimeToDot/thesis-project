package thesis.data.task.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import thesis.data.account.model.Account;

import javax.persistence.*;
import java.util.UUID;

@Getter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "task")
public class Task {

  @Id
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;

  @ManyToOne
  @JoinColumn(name = "form_id")
  private TaskForm form;

  private Long createdAt;

  private Long dateFrom;

  private Long dateTo;

  private String name;
}