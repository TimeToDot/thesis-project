package thesis.api.auth.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
public class AuthorizationRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    private String login;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private List<String> roles;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String sex;

    @NotBlank
    @JsonFormat(pattern = "yyyy-MM-dd")
    String birthDate;

    @NotBlank
    String birthPlace;

    @NotBlank
    String idCardNumber;

    @NotBlank
    String pesel;

    @NotBlank
    String street;

    @NotBlank
    String houseNumber;

    @NotBlank
    String apartmentNumber;

    @NotBlank
    String city;

    @NotBlank
    String postalCode;

    @NotBlank
    String country;

    @NotBlank
    String phoneNumber;

    @NotBlank
    @Email
    String privateEmail;

    @NotNull
    UUID positionId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    Date employmentDate;

    @NotBlank
    String contractType;

    @NotNull
    Integer workingTime;

    @NotNull
    Integer wage;

    @NotNull
    Integer payday;

    @NotNull
    String accountNumber;

}
