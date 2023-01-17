package thesis.api.employee.model;

import thesis.domain.employee.model.ContractTypeDTO;
import thesis.domain.employee.model.SimplePositionDTO;

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
        String sex,
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
        String country,
        String privateEmail,
        String accountNumber,
        ContractTypeDTO contractType,
        //BillingPeriodDTO billingPeriod,
        Integer workingTime,
        Integer wage,
        Integer payday,
        Boolean active
) {
}
