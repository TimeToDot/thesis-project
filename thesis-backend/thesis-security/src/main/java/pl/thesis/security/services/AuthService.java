package pl.thesis.security.services;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.thesis.data.account.*;
import pl.thesis.data.account.model.*;
import pl.thesis.data.position.PositionRepository;
import pl.thesis.data.position.model.Position;
import pl.thesis.data.role.RoleRepository;
import pl.thesis.data.role.model.Role;
import pl.thesis.data.role.model.RoleType;
import pl.thesis.security.services.model.AuthenticationSecurity;
import pl.thesis.security.services.model.AuthorizationSecurity;
import pl.thesis.security.config.JwtUtils;
import pl.thesis.security.services.model.ProjectPrivilege;
import pl.thesis.security.services.model.UserDetailsDefault;

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
    private final SexRepository sexRepository;
    private final CountryRepository countryRepository;
    private final ContractTypeRepository contractTypeRepository;
    private final AccountRoleRepository accountRoleRepository;

    public AuthenticationSecurity authenticateUser(UserDetailsDefault userDetails) {

        var authorities = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();


        var globalAuthorities = getGlobalAuthorities(authorities);
        var projectPrivileges = getProjectPrivileges(authorities);

        return AuthenticationSecurity.builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .globalAuthorities(globalAuthorities)
                .projectPrivileges(projectPrivileges)
                .build();

    }

    public boolean isAccountExist(String email){
        return accountRepository.existsByEmail( email);
    }

    @Transactional
    public Long addUser(AuthorizationSecurity authorizationSecurity){
        //vor better times
        /*var strRoles = authorizationDTO.roles();
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
        }*/

        Role role = roleRepository
                .findByName(RoleType.ROLE_GLOBAL_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        var position = positionRepository.findById(authorizationSecurity.positionId()).orElseThrow();

        var account = getAccount(authorizationSecurity, position);

        accountRepository.saveAndFlush(account);

        var accountDetails = getAccountDetails(authorizationSecurity, account);

        accountDetailsRepository.saveAndFlush(accountDetails);

        var accountRole= getAccountRole(account, role);

        accountRoleRepository.saveAndFlush(accountRole);

        return account.getId();
    }

    private AccountDetails getAccountDetails(AuthorizationSecurity authorizationSecurity, Account account) {
        Sex sex = sexRepository.findByName(authorizationSecurity.sex().name()).orElseThrow();
        Country country = countryRepository
                .findByName(authorizationSecurity.country().name())
                .orElseGet(() -> {
                    var c = Country.builder()
                        .id(authorizationSecurity.country().id())
                        .name(authorizationSecurity.country().name())
                        .build();

                    countryRepository.save(c);

                    return c;
                });

        ContractType contractType = contractTypeRepository.findById(authorizationSecurity.contractType().id()).orElseThrow();

        return AccountDetails.builder()
                .account(account)
                .name(authorizationSecurity.firstName())
                .middleName(authorizationSecurity.middleName())
                .surname(authorizationSecurity.lastName())
                .city(authorizationSecurity.city())
                .postalCode(authorizationSecurity.postalCode())
                .street(authorizationSecurity.street())
                .apartmentNumber(authorizationSecurity.apartmentNumber())
                .houseNumber(authorizationSecurity.houseNumber())
                .createdAt(new Date())
                .pesel(authorizationSecurity.pesel())
                .sex(sex)
                .phoneNumber(authorizationSecurity.phoneNumber())
                .taxNumber(authorizationSecurity.accountNumber())
                .country(country)
                .birthDate(authorizationSecurity.birthDate())
                .birthPlace(authorizationSecurity.birthPlace())
                .privateEmail(authorizationSecurity.privateEmail())
                .idCardNumber(authorizationSecurity.idCardNumber())
                .employmentDate(authorizationSecurity.employmentDate())
                .contractType(contractType)
                .wage(authorizationSecurity.wage())
                .workingTime(authorizationSecurity.workingTime())
                .payday(authorizationSecurity.payday())
                .phoneNumber(authorizationSecurity.phoneNumber())
                .imagePath(authorizationSecurity.imagePath())
                .build();
    }

    private AccountRole getAccountRole(Account account, Role role){
        return AccountRole.builder()
                .role(role)
                .account(account)
                .build();

    }

    private Account getAccount(AuthorizationSecurity authorizationSecurity, Position position) {


        return Account.builder()
                //.login(authorizationDTO.login())
                .pass(passwordEncoder.encode(authorizationSecurity.password()))
                .email(authorizationSecurity.email())
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

    private Map<Long, List<String>> getProjectPrivileges(List<String> authorities) {
        FuncProjectPrivilege funcProjectPrivilege = s -> {
            var tab = s.split("#");
            return new ProjectPrivilege(Long.parseLong(tab[0]), tab[1]);
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
