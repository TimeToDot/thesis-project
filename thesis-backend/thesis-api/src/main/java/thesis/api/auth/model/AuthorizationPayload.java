package thesis.api.auth.model;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import thesis.domain.employee.model.ContractTypeDTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Getter
public class AuthorizationPayload {

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    private String firstName;

    private String middleName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String sex;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @NotBlank
    private String birthPlace;

    @NotBlank
    private String idCardNumber;

    @NotBlank
    private String pesel;

    @NotBlank
    private String street;

    @NotBlank
    private String houseNumber;

    @NotBlank
    private String apartmentNumber;

    @NotBlank
    private String city;

    @NotBlank
    private String postalCode;

    @NotBlank
    private String country;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    @Email
    private String privateEmail;

    @NotNull
    private UUID positionId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date employmentDate;

    private ContractTypeDTO contractType;

    @NotNull
    private Integer workingTime;

    @NotNull
    private Integer wage;

    @NotNull
    private Integer payday;

    @NotNull
    private String accountNumber;

    private String imagePath;
}
