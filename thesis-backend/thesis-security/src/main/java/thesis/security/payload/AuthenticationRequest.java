package thesis.security.payload;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class AuthenticationRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
