package thesis.security.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import thesis.data.account.AccountRepository;
import thesis.data.account.AccountRoleRepository;
import thesis.data.account.model.Account;
import thesis.data.account.model.AccountDetails;
import thesis.data.account.model.AccountRole;
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
    private final AccountRoleRepository accountRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleTypeMapper roleTypeMapper;
    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody AuthorizationRequest authorizationRequest) {

        // TODO: 03/12/2022 spaghetti code

        if (accountRepository.existsByLogin(authorizationRequest.getLogin())) {
            return ResponseEntity.badRequest().body("Error: Login is already taken!");
        }

        if (accountRepository.existsByDetails_Email(authorizationRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        // Create new user's account

        var account = Account.builder()
                .login(authorizationRequest.getLogin())
                .details(new AccountDetails())
                .pass(passwordEncoder.encode(authorizationRequest.getPassword()))
                .build();

        List<String> strRoles = authorizationRequest.getRoles();
        List<Role> roles = new ArrayList<>();

        if (strRoles == null) {
            throw new RuntimeException("Error: Roles are empty!");
        } else {
            var eRoles = strRoles.stream().map(roleTypeMapper::map).map(RoleTypeDto::getRoleType);

            eRoles.forEach(role -> {
                switch (role) {
                    case ROLE_ADMIN -> {
                        Role adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                    }
                    case ROLE_MODERATOR -> {
                        Role modRole = roleRepository.findByName(RoleType.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                    }
                    case ROLE_USER -> {
                        Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                    }
                }
            });
        }
        accountRepository.save(account);

        roles.forEach(role -> {
            var accountRole = AccountRole.builder()
                    .account(account)
                    .role(role)
                    .build();

            accountRoleRepository.save(accountRole);
        });


        return ResponseEntity.ok("User registered successfully!");
    }
}
