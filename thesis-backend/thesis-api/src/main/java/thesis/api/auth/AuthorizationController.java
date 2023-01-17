package thesis.api.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import thesis.api.ThesisController;
import thesis.api.auth.mapper.AuthMapper;
import thesis.api.auth.model.AuthorizationPayload;
import thesis.data.account.AccountRepository;
import thesis.security.services.AuthService;

import javax.validation.Valid;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@RequestMapping("/api/authorization")
@RestController
public class AuthorizationController extends ThesisController {

    private final AuthService authService;
    private final AuthMapper authMapper;



    @Operation(summary = "register User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UUID.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content) })
    @PreAuthorize("hasAuthority('CAN_CREATE_USERS')")
    @PostMapping
    public ResponseEntity<UUID> registerUser(
            @RequestHeader UUID employeeId,
            @Valid @RequestBody AuthorizationPayload authorizationPayload
    ) {

        if (Boolean.TRUE.equals(authService.isAccountExist(authorizationPayload.getEmail()))) {
            return ResponseEntity.badRequest().build();
        }

        var dto = authMapper.mapToAuthorizationDTO(authorizationPayload);

        var response = authService.addUser(dto);


        return ResponseEntity.ok(response);
    }
}
