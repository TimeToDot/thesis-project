package thesis.security.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import thesis.security.config.JwtUtils;
import thesis.security.payload.AuthenticationRequest;
import thesis.security.payload.AuthenticationResponse;
import thesis.security.services.model.ProjectPrivilege;
import thesis.security.services.model.UserDetailsDefault;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    // TODO: 03/12/2022 spaghetti code

    @PostMapping(
            value = "/signin",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<AuthenticationResponse> authenticateUser(@Valid @RequestBody AuthenticationRequest authenticationRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var userDetails = (UserDetailsDefault) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        var authorities = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority);


        var globalAuthorities = getGlobalAuthorities(authorities);
        var projectPrivileges = getProjectPrivileges(authorities);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(AuthenticationResponse.builder()
                        .id(userDetails.getId())
                        .username(userDetails.getUsername())
                        .email(userDetails.getEmail())
                        .globalAuthorities(globalAuthorities)
                        .projectPrivileges(projectPrivileges)
                        .build()
                );
    }

    @PostMapping(
            value = "/signout"
    )
    public ResponseEntity<String> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("You've been signed out!");
    }

    private List<String> getGlobalAuthorities(Stream<String> authorities) {
        return authorities
                .filter(s -> s.startsWith("CAN_"))
                .toList();
    }

    private Map<UUID, List<String>> getProjectPrivileges(Stream<String> authorities) {
        FuncProjectPrivilege funcProjectPrivilege = s -> {
            var tab = s.split("#");
            return new ProjectPrivilege(UUID.fromString(tab[0]), tab[1]);
        };

        return authorities
                .filter(s -> !s.startsWith("CAN_"))
                .map(funcProjectPrivilege::toPrivilege)
                .collect(Collectors.groupingBy(
                                ProjectPrivilege::id,
                                Collectors.mapping(
                                        ProjectPrivilege::privilege,
                                        Collectors.toList()
                                )
                        )
                );
    }


    @FunctionalInterface
    interface FuncProjectPrivilege {
        ProjectPrivilege toPrivilege(String s);
    }

}
