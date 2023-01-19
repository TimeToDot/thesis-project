package thesis.domain.project.model.employee;

import lombok.Builder;
import thesis.domain.employee.model.SimplePositionDTO;
import thesis.security.services.model.ContractDTO;

import java.util.Date;
import java.util.UUID;

@Builder
public record EmployeeDTO(
    UUID employeeId,
    String firstName,
    String lastName,
    String email,
    String imagePath,
    SimplePositionDTO position,
    Date employmentDate,
    ContractDTO contractType,
    Integer workingTime,
    Integer wage,
    Boolean active
    ){

}
