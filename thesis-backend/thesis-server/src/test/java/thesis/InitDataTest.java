package thesis;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import thesis.data.account.AccountDetailsRepository;
import thesis.data.account.AccountRepository;
import thesis.data.account.model.Account;
import thesis.data.account.model.AccountDetails;
import thesis.data.permission.PrivilegeRepository;
import thesis.data.permission.model.Privilege;
import thesis.data.permission.model.PrivilegeType;
import thesis.data.role.RoleRepository;
import thesis.data.role.model.Role;
import thesis.data.role.model.RoleType;

import java.util.List;
import java.util.UUID;

import static thesis.data.permission.model.PrivilegeType.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ThesisApplication.class)
public class InitDataTest {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountDetailsRepository accountDetailsRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void qcontextLoads() {
        initPrivileges();
        initRoles();
        initAccounts();
    }

    ;

    private void initAccounts() {
        var adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN).orElseThrow(RuntimeException::new);
        var modRole = roleRepository.findByName(RoleType.ROLE_MODERATOR).orElseThrow(RuntimeException::new);
        var userRole = roleRepository.findByName(RoleType.ROLE_USER).orElseThrow(RuntimeException::new);


        var admin = createAccount("admin", "admin", adminRole);
        var mod = createAccount("moderator", "moderator", modRole);
        var user = createAccount("user", "user", userRole);

        accountRepository.save(admin);
        accountRepository.save(mod);
        accountRepository.save(user);

        var adminDetails = createAccountDetails(admin.getId(),"asd", "admin@email.com", "123123123123");
        var modDetails = createAccountDetails(mod.getId(), "asd", "mod@email.com", "123123123123");
        var userDetails = createAccountDetails(mod.getId(), "asd", "mod@email.com", "123123123123");

        accountDetailsRepository.save(adminDetails);
        accountDetailsRepository.save(modDetails);
        accountDetailsRepository.save(userDetails);

    }

    private void initPrivileges() {
        var privilegeList = List.of(
                Privilege.builder().name(CAN_ADMIN_PROJECTS).build(),
                Privilege.builder().name(PrivilegeType.CAN_ADMIN_USERS).build(),
                Privilege.builder().name(PrivilegeType.CAN_READ_ADMIN).build(),
                Privilege.builder().name(PrivilegeType.CAN_READ_PROJECT).build(),
                Privilege.builder().name(PrivilegeType.CAN_MANAGE_PROJECT_USERS).build(),
                Privilege.builder().name(PrivilegeType.CAN_MANAGE_TASKS).build()
        );
        privilegeRepository.saveAll(privilegeList);
    }

    private void initRoles() {
        var roleList = List.of(
            Role.builder()
                    .name(RoleType.ROLE_ADMIN)
                    .privileges(List.of(
                            privilegeRepository.findByName(CAN_ADMIN_PROJECTS.name()),
                            privilegeRepository.findByName(CAN_ADMIN_USERS.name()),
                            privilegeRepository.findByName(CAN_READ_ADMIN.name()),
                            privilegeRepository.findByName(CAN_READ_PROJECT.name())
                    ))
                    .build(),
            Role.builder()
                    .name(RoleType.ROLE_MODERATOR)
                    .privileges(List.of(
                            privilegeRepository.findByName(CAN_MANAGE_TASKS.name()),
                            privilegeRepository.findByName(CAN_MANAGE_PROJECT_USERS.name()),
                            privilegeRepository.findByName(CAN_READ_PROJECT.name())
                    ))
                    .build(),
            Role.builder()
                    .name(RoleType.ROLE_USER)
                    .privileges(List.of(
                            privilegeRepository.findByName(CAN_READ_PROJECT.name())
                    ))
                    .build()
        );
        roleRepository.saveAll(roleList);
    }

    private Account createAccount(String login, String pass, Role role){
        return Account.builder()
                .login(login)
                .pass(passwordEncoder.encode(pass))
                .email("siema@email.com")
                .roles(List.of(role))
                .build();
    }

    private AccountDetails createAccountDetails(UUID id, String city, String email, String pesel){
        return AccountDetails.builder()
                .accountId(id)
                .city(city)
                .pesel(pesel)
                .build();

    }
}