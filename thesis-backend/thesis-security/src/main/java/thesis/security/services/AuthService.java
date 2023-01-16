package thesis.security.services;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thesis.data.account.AccountDetailsRepository;
import thesis.data.account.AccountRepository;
import thesis.data.account.model.Account;
import thesis.data.account.model.AccountDetails;
import thesis.data.account.model.StatusType;
import thesis.data.position.PositionRepository;
import thesis.data.position.model.Position;
import thesis.data.role.RoleRepository;
import thesis.data.role.model.Role;
import thesis.data.role.model.RoleType;
import thesis.security.services.model.AuthenticationDTO;
import thesis.security.services.model.AuthorizationDTO;
import thesis.security.config.JwtUtils;
import thesis.security.services.model.ProjectPrivilege;
import thesis.security.services.model.UserDetailsDefault;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final PositionRepository positionRepository;

    private final AccountDetailsRepository accountDetailsRepository;

    public AuthenticationDTO authenticateUser(UserDetailsDefault userDetails) {


        var authorities = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();


        var globalAuthorities = getGlobalAuthorities(authorities);
        var projectPrivileges = getProjectPrivileges(authorities);

        return AuthenticationDTO.builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .globalAuthorities(globalAuthorities)
                .projectPrivileges(projectPrivileges)
                .build();

    }

    public boolean isAccountExist(String login){
        return accountRepository.existsByLogin(login);
    }

    public boolean isEmailExist(String email){
        return accountRepository.existsByEmail(email);
    }


    @Transactional
    public UUID addUser(AuthorizationDTO authorizationDTO){
        var strRoles = authorizationDTO.roles();
        List<Role> roles = new ArrayList<>();


        if (strRoles == null) {
            throw new RuntimeException("Error: Roles are empty!");
        } else {
            var eRoles = strRoles.stream().map(RoleType::valueOf).toList();

            for (RoleType role : eRoles) {
                if (role == RoleType.ROLE_GLOBAL_ADMIN) {
                    Role adminRole = roleRepository
                            .findByName(RoleType.ROLE_GLOBAL_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                } else if (role == RoleType.ROLE_GLOBAL_USER) {
                    Role modRole = roleRepository
                            .findByName(RoleType.ROLE_GLOBAL_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(modRole);
                }
            }
        }

        var position = positionRepository.findById(authorizationDTO.positionId()).orElseThrow();
        var account = getAccount(authorizationDTO, roles, position);

        accountRepository.saveAndFlush(account);

        var accountDetails = getAccountDetails(authorizationDTO, account);

        accountDetailsRepository.saveAndFlush(accountDetails);

        return account.getId();
    }

    private AccountDetails getAccountDetails(AuthorizationDTO authorizationDTO, Account account) {
        return AccountDetails.builder()
                .account(account)
                .name(authorizationDTO.firstName())
                .surname(authorizationDTO.lastName())
                .city(authorizationDTO.city())
                .postalCode(authorizationDTO.postalCode())
                .street(authorizationDTO.street() + " " + authorizationDTO.apartmentNumber())
                .createdAt(new Date())
                .pesel(authorizationDTO.pesel())
                .sex(authorizationDTO.sex())
                .phoneNumber(authorizationDTO.phoneNumber())
                .taxNumber(authorizationDTO.accountNumber())
                .build();
    }

    private Account getAccount(AuthorizationDTO authorizationDTO, List<Role> roles, Position position) {
        return Account.builder()
                .login(authorizationDTO.login())
                .pass(passwordEncoder.encode(authorizationDTO.password()))
                .email(authorizationDTO.email())
                .roles(roles)
                .position(position)
                .status(StatusType.ENABLE)
                .build();
    }

    private List<String> getGlobalAuthorities(List<String> authorities) {
        return authorities
                .stream()
                .filter(s -> s.startsWith("CAN_"))
                .toList();
    }

    private Map<UUID, List<String>> getProjectPrivileges(List<String> authorities) {
        FuncProjectPrivilege funcProjectPrivilege = s -> {
            var tab = s.split("#");
            return new ProjectPrivilege(UUID.fromString(tab[0]), tab[1]);
        };

        return authorities
                .stream()
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