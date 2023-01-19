package thesis.domain.employee.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import thesis.data.account.model.ContractType;
import thesis.data.account.model.Country;
import thesis.data.account.model.Sex;
import thesis.security.services.model.ContractDTO;
import thesis.security.services.model.ContractTypeDTO;
import thesis.security.services.model.SexDTO;

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
        UUID positionId,

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
