package thesis.api.employee.model.temp;

import thesis.security.services.model.ContractDTO;
import thesis.security.services.model.CountryDTO;
import thesis.security.services.model.SexDTO;
import thesis.security.services.model.SimplePositionAuth;

import java.util.Date;
import java.util.UUID;

public record AuthorizationTemp(
        String email,
        String password,
        String firstName,
        String middleName,
        String lastName,
        SexDTO sex,
        Date birthDate,
        String birthPlace,
        String idCardNumber,
        String pesel,
        String street,
        String houseNumber,
        String apartmentNumber,
        String city,
        String postalCode,
        CountryDTO country,
        String phoneNumber,
        String privateEmail,
        SimplePositionAuth position,
        Date employmentDate,
        ContractDTO contractType,
        Integer workingTime,
        Integer wage,
        Integer payday,
        String accountNumber,
        String imagePath
) {
}
