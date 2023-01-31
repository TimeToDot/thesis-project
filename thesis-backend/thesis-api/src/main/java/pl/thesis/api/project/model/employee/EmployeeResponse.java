package pl.thesis.api.project.model.employee;

import lombok.Builder;
import pl.thesis.api.employee.model.SimplePositionResponse;
import pl.thesis.api.global.model.ContractResponse;

import java.util.Date;

@Builder
public record EmployeeResponse(
    String employeeId,
    String firstName,
    String lastName,
    String email,
    String imagePath,
    SimplePositionResponse position,
    Date employmentDate,
    ContractResponse contractType,
    Integer workingTime,
    Integer wage,
    Boolean active
    ){

}
