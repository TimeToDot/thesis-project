package thesis.api.employee.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.UUID;

public record EmployeeUpdatePayload(

        @NotBlank
        @Email
        String email,

        String firstName,
        String lastName,
        String sex,

        @JsonFormat(pattern = "yyyy-MM-dd")
        String birthDate,
        String birthPlace,
        String idCardNumber,
        String pesel,
        String street,
        String houseNumber,
        String apartmentNumber,
        String city,
        String postalCode,
        String country,
        String phoneNumber,
        UUID positionId,

        @JsonFormat(pattern = "yyyy-MM-dd")
        Date employmentDate,
        String contractType,
        Integer workingTime,
        Integer wage,
        Integer payday,
        String accountNumber

) {
}
