package pl.thesis.domain.employee.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(description = "Information about account.")
public record EmployeeDTO(
        Long id,
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
        Integer workingTime,
        Integer wage,
        Integer payday,
        Boolean active
) {

}