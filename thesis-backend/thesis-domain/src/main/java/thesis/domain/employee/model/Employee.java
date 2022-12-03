package thesis.domain.employee.model;

import io.swagger.v3.oas.annotations.media.Schema;
import thesis.data.account.model.StatusType;

import java.util.UUID;

@Schema(description = "Information about account.")
public record Employee(

  UUID id,
  String login,
  String pass,
  StatusType status,
  String email) {

}