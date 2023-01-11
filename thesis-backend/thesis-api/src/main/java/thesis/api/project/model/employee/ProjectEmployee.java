package thesis.api.project.model.employee;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public record ProjectEmployee (
    UUID employeeId,
    String firstName,
    String lastName,
    String email,
    String imagePath,
    String position,
    Date employmentDate,
    LocalDateTime workingTime,
    Boolean active
    ){

}
