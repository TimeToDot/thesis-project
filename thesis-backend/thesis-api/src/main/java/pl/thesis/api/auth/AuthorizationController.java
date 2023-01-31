package pl.thesis.api.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.thesis.api.ThesisController;
import pl.thesis.api.auth.mapper.AuthMapper;
import pl.thesis.api.auth.model.AuthorizationPayload;
import pl.thesis.security.services.AuthService;

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
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid positionId supplied",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content) })
    @PreAuthorize("hasAuthority('CAN_CREATE_USERS')")
    @PostMapping
    public ResponseEntity<String> registerUser(
            @RequestHeader String employeeId,
            @Valid @RequestBody AuthorizationPayload authorizationPayload
    ) {
/*
        if (Boolean.TRUE.equals(authService.isAccountExist(authorizationPayload.getEmail()))) {
            return ResponseEntity.badRequest().build();
        }

        var dto = authMapper.mapToAuthorizationDTO(authorizationPayload);

        var response = authService.addUser(dto);


        return ResponseEntity.ok(response);*/

        return ResponseEntity.ok().build();
    }
}
