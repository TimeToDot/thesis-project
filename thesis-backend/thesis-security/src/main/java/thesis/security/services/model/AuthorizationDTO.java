package thesis.security.services.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record AuthorizationDTO(

        String login,
        String email,
        List<String> roles,
        String password,
        String firstName,
        String middleName,
        String lastName,
        String sex,
        Date birthDate,
        String birthPlace,
        String idCardNumber,
        String pesel,
        String street,
        String houseNumber,
        String apartmentNumber,
        String city,
        String postalCode,
        String country,
        String phoneNumber,
        String privateEmail,
        UUID positionId,
        Date employmentDate,
        String contractType,
        Integer workingTime,
        Integer wage,
        Integer payday,
        String accountNumber,
        String imagePath
) {
}
