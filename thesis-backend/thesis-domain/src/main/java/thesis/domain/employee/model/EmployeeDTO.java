package thesis.domain.employee.model;

import io.swagger.v3.oas.annotations.media.Schema;
import thesis.data.account.model.StatusType;

import javax.validation.constraints.NotNull;

@Schema(description = "Information about account.")
public record EmployeeDTO(
        String firstName,
        String lastName,
        String email,
        String password,
        String positionId,
        String employmentDate,
        String image,
        String sex,
        String birthDate,
        String phoneNumber,
        String city,
        String street,
        String postalCode,
        String country,
        String accountNumber) {

}