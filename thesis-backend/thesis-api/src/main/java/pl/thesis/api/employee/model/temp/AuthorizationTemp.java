package pl.thesis.api.employee.model.temp;

import pl.thesis.security.services.model.ContractSec;
import pl.thesis.security.services.model.CountrySec;
import pl.thesis.security.services.model.SexSec;
import pl.thesis.security.services.model.SimplePositionAuth;

import java.util.Date;

public record AuthorizationTemp(
        String email,
        String password,
        String firstName,
        String middleName,
        String lastName,
        SexSec sex,
        Date birthDate,
        String birthPlace,
        String idCardNumber,
        String pesel,
        String street,
        String houseNumber,
        String apartmentNumber,
        String city,
        String postalCode,
        CountrySec country,
        String phoneNumber,
        String privateEmail,
        SimplePositionAuth position,
        Date employmentDate,
        ContractSec contractType,
        Integer workingTime,
        Integer wage,
        Integer payday,
        String accountNumber,
        String imagePath
) {
}
