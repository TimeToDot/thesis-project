package pl.thesis.api.employee.model;

import pl.thesis.api.global.model.ContractResponse;
import pl.thesis.security.services.model.CountryDTO;
import pl.thesis.security.services.model.SexDTO;

import java.util.Date;
import java.util.UUID;

public record EmployeeResponse(

        String id,
        String firstName,
        String lastName,
        String middleName,
        String email,
        String password,
        String idCardNumber,
        String pesel,
        SexDTO sex,
        SimplePositionResponse position,
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
        ContractResponse contractType,
        Integer workingTime,
        Integer wage,
        Integer payday,
        Boolean active
) {
}
