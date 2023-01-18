package thesis.domain.employee.model;

import io.swagger.v3.oas.annotations.media.Schema;
import thesis.data.account.model.Contract;

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
        Contract contractType,
        //BillingPeriodDTO billingPeriod,
        Integer workingTime,
        Integer wage,
        Integer payday,
        Boolean active
) {

}