package pl.thesis.domain.project.model.employee;

import lombok.Builder;
import pl.thesis.domain.employee.model.ContractDTO;
import pl.thesis.domain.employee.model.SimplePositionDTO;

import java.util.Date;

@Builder
public record EmployeeDTO(
    Long employeeId,
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
