package thesis.domain.account.model;

import io.swagger.v3.oas.annotations.media.Schema;
import thesis.data.account.model.StatusType;

import java.util.UUID;

@Schema(description = "Information about account.")
public record AccountDto(

  UUID id,
  String login,
  String pass,
  StatusType status,
  String email) {

}