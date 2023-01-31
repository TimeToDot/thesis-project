package pl.thesis.domain.project.model.employee;

import lombok.Builder;
import pl.thesis.domain.employee.model.SimplePositionDTO;
import pl.thesis.security.services.model.ContractDTO;

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
