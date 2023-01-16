package thesis.domain.employee.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.UUID;

public record EmployeeUpdatePayloadDTO(

        @NotBlank
        @Email
        String email,

        String firstName,
        String middleName,
        String lastName,
        String sex,

        @JsonFormat(pattern = "yyyy-MM-dd")
        Date birthDate,
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
        @Email
        String privateEmail,
        UUID positionId,

        @JsonFormat(pattern = "yyyy-MM-dd")
        Date employmentDate,
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date exitDate,
        String contractType,
        Integer workingTime,
        Integer wage,
        Integer payday,
        String accountNumber,
        Boolean active
) {
}
