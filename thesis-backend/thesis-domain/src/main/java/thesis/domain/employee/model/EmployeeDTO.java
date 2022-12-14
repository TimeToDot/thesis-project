package thesis.domain.employee.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Information about account.")
public record EmployeeDTO(
        String firstName,
        String lastName,
        String email,
        String password,
        List<String> positionIds,
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