package pl.thesis.api.employee.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import pl.thesis.api.position.model.PositionResponse;
import pl.thesis.data.account.model.Country;
import pl.thesis.domain.position.model.PositionResponseDTO;
import pl.thesis.security.services.model.ContractDTO;
import pl.thesis.security.services.model.SexDTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.UUID;

public record EmployeeUpdatePayload(

        @NotBlank
        @Email
        String email,

        String firstName,
        String middleName,
        String lastName,
        SexDTO sex,

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
        Country country,
        String phoneNumber,
        @Email
        String privateEmail,
        PositionResponse position,

        @JsonFormat(pattern = "yyyy-MM-dd")
        Date employmentDate,
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date exitDate,
        ContractDTO contractType,
        Integer workingTime,
        Integer wage,
        Integer payday,
        String accountNumber,
        Boolean active

) {
}
