package thesis.domain.employee.model;

import io.swagger.v3.oas.annotations.media.Schema;
import thesis.security.services.model.ContractDTO;
import thesis.security.services.model.CountryDTO;
import thesis.security.services.model.SexDTO;

import java.util.Date;
import java.util.UUID;

@Schema(description = "Information about account.")
public record EmployeeDTO(
        UUID id,
        String firstName,
        String lastName,
        String middleName,
        String email,
        String password,
        String idCardNumber,
        String pesel,
        SexDTO sex,
        String position,
        Date employmentDate,
        Date exitDate,
        String imagePath,
        String birthDate,
        String birthPlace,
        String phoneNumber,
        String city,
        String street,
        String houseNumber,
        String apartmentNumber,
        String postalCode,
        CountryDTO country,
        String privateEmail,
        String accountNumber,
        ContractDTO contractType,
        //BillingPeriodDTO billingPeriod,
        Integer workingTime,
        Integer wage,
        Integer payday,
        Boolean active
) {

}