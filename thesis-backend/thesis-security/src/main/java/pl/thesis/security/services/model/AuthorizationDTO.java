package pl.thesis.security.services.model;

import java.util.Date;
import java.util.UUID;

public record AuthorizationDTO(

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
        UUID positionId,
        Date employmentDate,
        ContractDTO contractType,
        Integer workingTime,
        Integer wage,
        Integer payday,
        String accountNumber,
        String imagePath
) {
}
