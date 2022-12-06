package thesis.security.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import thesis.data.account.AccountRepository;
import thesis.data.account.model.Account;
import thesis.data.role.RoleRepository;
import thesis.data.role.model.Role;
import thesis.data.role.model.RoleType;
import thesis.security.payload.AuthorizationRequest;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@RequestMapping("/api/authorization")
@RestController
public class AuthorizationController {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody AuthorizationRequest authorizationRequest) {

        // TODO: 03/12/2022 spaghetti code

        if (accountRepository.existsByLogin(authorizationRequest.getLogin())) {
            return ResponseEntity.badRequest().body("Error: Login is already taken!");
        }

        if (accountRepository.existsByEmail(authorizationRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        // Create new user's account



        List<String> strRoles = authorizationRequest.getRoles();
        List<Role> roles = new ArrayList<>();

        if (strRoles == null) {
            throw new RuntimeException("Error: Roles are empty!");
        } else {
            var eRoles = strRoles.stream().map(RoleType::valueOf).toList();

            eRoles.forEach(role -> {
                switch (role) {
                    case ROLE_GLOBAL_ADMIN -> {
                        Role adminRole = roleRepository.findByName(RoleType.ROLE_GLOBAL_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                    }
                    case ROLE_GLOBAL_USER -> {
                        Role modRole = roleRepository.findByName(RoleType.ROLE_GLOBAL_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                    }
                }
            });
        }
        var account = Account.builder()
                .login(authorizationRequest.getLogin())
                .pass(passwordEncoder.encode(authorizationRequest.getPassword()))
                .roles(roles)
                .build();

        accountRepository.save(account);



        return ResponseEntity.ok("User registered successfully!");
    }
}
