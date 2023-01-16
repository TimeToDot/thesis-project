package thesis.api.auth.model;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class AuthenticationPayload {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
