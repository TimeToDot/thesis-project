package pl.thesis.api.employee.model;

import pl.thesis.api.employee.CountryResponse;
import pl.thesis.api.employee.SexResponse;
import pl.thesis.api.global.model.ContractResponse;

import java.util.Date;

public record EmployeeResponse(

        String id,
        String firstName,
        String lastName,
        String middleName,
        String email,
        String password,
        String idCardNumber,
        String pesel,
        SexResponse sex,
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
        CountryResponse country,
        String privateEmail,
        String accountNumber,
        ContractResponse contractType,
        Integer workingTime,
        Integer wage,
        Integer payday,
        Boolean active
) {
}
