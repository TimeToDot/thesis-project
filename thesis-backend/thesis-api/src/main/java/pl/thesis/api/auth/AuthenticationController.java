package pl.thesis.api.auth;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.thesis.api.auth.mapper.AuthMapper;
import pl.thesis.api.auth.model.AuthenticationPayload;
import pl.thesis.api.auth.model.AuthenticationResponse;
import pl.thesis.security.services.AuthService;
import pl.thesis.security.config.JwtUtils;
import pl.thesis.security.services.model.UserDetailsDefault;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    private final AuthService authService;

    private final AuthMapper authMapper;

    @PostMapping(
            value = "/login",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<AuthenticationResponse> authenticateUser(@Valid @RequestBody AuthenticationPayload authenticationPayload) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationPayload.getUsername(), authenticationPayload.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var userDetails = (UserDetailsDefault) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        var dto = authService.authenticateUser(userDetails);
        var response = authMapper.map(dto);

        return ResponseEntity.ok()
                .header(HttpHeaders.COOKIE, jwtCookie.toString())
                .body(response);
    }
}
