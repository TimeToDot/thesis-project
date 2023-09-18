package pl.thesis.security.services.model;

import java.util.Date;

public record AuthorizationSecurity(

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
        Long positionId,
        Date employmentDate,
        ContractSec contractType,
        Integer workingTime,
        Integer wage,
        Integer payday,
        String accountNumber,
        String imagePath
) {
}
