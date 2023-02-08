package thesis.api.employee.model;

import javax.validation.constraints.NotNull;
import java.util.List;

public record EmployeeResponse(

        @NotNull
        String firstName,
        @NotNull
        String lastName,
        @NotNull
        String email,
        @NotNull
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
        String accountNumber
) {
}
