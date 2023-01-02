package thesis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import thesis.data.account.AccountDetailsRepository;
import thesis.data.account.AccountRepository;
import thesis.data.account.model.Account;
import thesis.data.account.model.AccountDetails;
import thesis.data.account.model.StatusType;
import thesis.data.position.PositionRepository;
import thesis.data.position.model.Position;
import thesis.data.project.ProjectAccountRoleRepository;
import thesis.data.project.ProjectDetailsRepository;
import thesis.data.project.ProjectRepository;
import thesis.data.project.model.Project;
import thesis.data.project.model.ProjectAccountRole;
import thesis.data.project.model.ProjectDetails;
import thesis.data.role.RoleRepository;
import thesis.data.role.model.Role;
import thesis.data.role.model.RoleType;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ThesisApplication.class)
public class InitDataTest {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountDetailsRepository accountDetailsRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectDetailsRepository projectDetailsRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private ProjectAccountRoleRepository projectAccountRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void contextLoads() {
        initData();
    }


    private void initData() {
        var accounts = initAccount();
        var projects = initProjects(accounts);
        var projectAdminRole = roleRepository.findByName(RoleType.ROLE_PROJECT_ADMIN).orElseThrow();
        var projectModRole = roleRepository.findByName(RoleType.ROLE_PROJECT_MODERATOR).orElseThrow();
        var projectUserRole = roleRepository.findByName(RoleType.ROLE_PROJECT_USER).orElseThrow();

        projects.forEach(project -> {
            var projectAccountRoleForAdmin = ProjectAccountRole.builder()
                    .project(project)
                    .account(accounts.get(0))
                    .role(projectAdminRole)
                    .build();

            var projectAccountRoleForMod = ProjectAccountRole.builder()
                    .project(project)
                    .account(accounts.get(1))
                    .role(projectModRole)
                    .build();


            var projectAccountRoleForUser = ProjectAccountRole.builder()
                    .project(project)
                    .account(accounts.get(2))
                    .role(projectUserRole)
                    .build();

            var list = List.of(projectAccountRoleForAdmin, projectAccountRoleForMod, projectAccountRoleForUser);

            projectAccountRoleRepository.saveAll(list);

            list.forEach(projectAccountRole -> Assert.assertNotNull(projectAccountRole.getId()));
        });
    }


    private List<Account> initAccount(){
        var adminRole = roleRepository.findByName(RoleType.ROLE_GLOBAL_ADMIN).orElseThrow(RuntimeException::new);
        var userRole = roleRepository.findByName(RoleType.ROLE_GLOBAL_USER).orElseThrow(RuntimeException::new);

        var positionManager = positionRepository.findByName("MANAGER_II").orElseThrow(AssertionError::new);
        var positionEmployee = positionRepository.findByName("EMPLOYEE_I").orElseThrow(AssertionError::new);

        var admin = createAccount("admin", "admin", "siema@email.com", adminRole, null);
        var mod = createAccount("moderator", "moderator", "siema@email.com", userRole, positionManager);
        var user = createAccount("user", "user", "siema@email.com", userRole, positionEmployee);

        accountRepository.save(admin);
        Assert.assertNotNull(admin.getId());

        accountRepository.save(mod);
        Assert.assertNotNull(mod.getId());

        accountRepository.save(user);
        Assert.assertNotNull(user.getId());

        var adminDetails = createAccountDetails(admin,"Krakow", "123123123123");
        var modDetails = createAccountDetails(mod, "Limanowa", "456456456456");
        var userDetails = createAccountDetails(user, "Przyszowa", "678678678678");

        accountDetailsRepository.save(adminDetails);
        accountDetailsRepository.save(modDetails);
        accountDetailsRepository.save(userDetails);

        return List.of(admin, mod, user);

    }
    private List<Project> initProjects(List<Account> accounts){
        var project1 = Project.builder()
                .name("Project1")
                .owner(accounts.get(0))
                .build();

        var project2 = Project.builder()
                .name("Project2")
                .owner(accounts.get(1))
                .build();

        var project3 = Project.builder()
                .name("Project3")
                .owner(accounts.get(1))
                .build();

        var projects = List.of(project1, project2);

        projectRepository.saveAll(projects);

        projects.forEach(project -> Assert.assertNotNull(project.getId()));

        var projectDetails1 = ProjectDetails.builder()
                .project(project1)
                .options("Jakieś cuda na kiju;powiedzmy że oddzielamy średnikiem")
                .build();
        var projectDetails2 = ProjectDetails.builder()
                .project(project2)
                .options("Drugi projekcik i jego opcja;druga opcja")
                .build();

        projectDetailsRepository.saveAll(List.of(projectDetails1, projectDetails2));

        return projects;
    }

    private Account createAccount(String login, String pass, String email, Role role, Position position){
        return Account.builder()
                .login(login)
                .pass(passwordEncoder.encode(pass))
                .email("siema." +login +"@email.com")
                .roles(List.of(role))
                .status(StatusType.ENABLE)
                .positions(position == null ? null : List.of(position))
                .build();
    }

    private AccountDetails createAccountDetails(Account account, String city, String pesel){
        return AccountDetails.builder()
                .account(account)
                .city(city)
                .pesel(pesel)
                .phoneNumber("123123123")
                .street("Domyslna" + account.getLogin())
                .postalCode("12-123")
                .taxNumber("123123123123123123")
                .sex("male")
                .surname("Domyśliciel")
                .name("Domyslaw-" + account.getLogin())
                .city("Domyslice" + account.getLogin())
                .build();

    }
}