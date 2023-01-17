package thesis.domain.project.model.employee;

import lombok.Builder;
import thesis.domain.employee.model.ContractTypeDTO;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Builder
public record EmployeeDTO(
    UUID employeeId,
    String firstName,
    String lastName,
    String email,
    String imagePath,
    UUID position,
    Date employmentDate,
    ContractTypeDTO contractTypeDTO,
    Integer workingTime,
    Integer wage,
    Boolean active
    ){

}
