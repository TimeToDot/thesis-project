package thesis.api.employee.model;

import thesis.security.services.model.ContractDTO;
import thesis.security.services.model.ContractTypeDTO;
import thesis.domain.employee.model.SimplePositionDTO;
import thesis.security.services.model.CountryDTO;
import thesis.security.services.model.SexDTO;

import java.util.Date;
import java.util.UUID;

public record EmployeeResponse(

        UUID id,
        String firstName,
        String lastName,
        String middleName,
        String email,
        String password,
        String idCardNumber,
        String pesel,
        SexDTO sex,
        SimplePositionDTO position,
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
