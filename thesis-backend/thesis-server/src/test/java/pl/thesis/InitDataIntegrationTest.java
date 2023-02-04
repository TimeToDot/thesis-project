package pl.thesis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import pl.thesis.ThesisApplication;
import pl.thesis.data.account.*;
import pl.thesis.data.account.model.*;
import pl.thesis.data.project.model.*;
import pl.thesis.data.task.model.*;
import pl.thesis.data.position.PositionRepository;
import pl.thesis.data.position.model.Position;
import pl.thesis.data.project.AccountProjectRepository;
import pl.thesis.data.project.BillingPeriodRepository;
import pl.thesis.data.project.ProjectDetailsRepository;
import pl.thesis.data.project.ProjectRepository;
import pl.thesis.data.role.RoleRepository;
import pl.thesis.data.role.model.Role;
import pl.thesis.data.role.model.RoleType;
import pl.thesis.data.task.TaskFormDetailsRepository;
import pl.thesis.data.task.TaskFormRepository;
import pl.thesis.data.task.TaskRepository;
import pl.thesis.domain.paging.PagingSettings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ThesisApplication.class)
public class InitDataIntegrationTest {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountDetailsRepository accountDetailsRepository;
    @Autowired
    private AccountRoleRepository accountRoleRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectDetailsRepository projectDetailsRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private AccountProjectRepository accountProjectRepository;

    @Autowired
    private TaskFormRepository taskFormRepository;

    @Autowired
    private TaskFormDetailsRepository taskFormDetailsRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BillingPeriodRepository billingPeriodRepository;

    @Autowired
    private SexRepository sexRepository;

    @Autowired
    private ContractTypeRepository contractTypeRepository;

    @Autowired
    private CountryRepository countryRepository;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    //@Transactional
    public void contextLoads() {
        initData();
    }


    private void initData() {
        if (accountRepository.findByEmail("admin@email.com").isPresent()){
            log.info("Initialization data skipped. Entities already there");
            return;
        }

        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        var accounts = initAccount();
        var projects = initProjects(accounts);
        var taskForms = initTaskForms(projects);
        var projectAdminRole = roleRepository.findByName(RoleType.ROLE_PROJECT_ADMIN).orElseThrow();
        var projectModRole = roleRepository.findByName(RoleType.ROLE_PROJECT_MODERATOR).orElseThrow();
        var projectUserRole = roleRepository.findByName(RoleType.ROLE_PROJECT_USER).orElseThrow();

        var accountsRoles = Map.of(
                projectAdminRole, accounts.get(0),
                projectModRole, accounts.get(1),
                projectUserRole, accounts.get(2)
        );

        for (Account account : accounts) {
            if (accountProjectRepository.findByAccountId(account.getId()).isPresent()) {
                return;
            }
        }
        var accountProjects = initAccountProjects(projects,accountsRoles);


        for (Account account : accounts) {
            var tasks = initTasks(account, taskForms);

            taskRepository.saveAll(tasks);
        }
        ////tasks




    }

    private List<AccountProject> initAccountProjects(List<Project> projects, Map<Role, Account> roleAccountMap) {
        List<AccountProject> accountProjects = new ArrayList<>();

        try {
        for (Project project : projects) {

            for (Map.Entry<Role, Account> entry : roleAccountMap.entrySet()) {
                Role role = entry.getKey();
                Account account = entry.getValue();

                accountProjects.add(createProjectAccount(project, account, role, "2022-12-12 14:10:00"));
            }
        }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        accountProjectRepository.saveAll(accountProjects);

        accountProjects.forEach(projectAccountRole -> Assert.assertNotNull(projectAccountRole.getId()));

        return accountProjects;
    }

    private AccountProject createProjectAccount(Project project, Account account, Role projectAdminRole, String joinDate) throws ParseException {
        return AccountProject.builder()
                .project(project)
                .account(account)
                .role(projectAdminRole)
                .joinDate(sdf.parse(joinDate))
                .status(ProjectAccountStatus.ACTIVE)
                .build();
    }


    private List<Account> initAccount(){
        var adminRole = roleRepository.findByName(RoleType.ROLE_GLOBAL_ADMIN).orElseThrow(RuntimeException::new);
        var userRole = roleRepository.findByName(RoleType.ROLE_GLOBAL_USER).orElseThrow(RuntimeException::new);

        var positionManager = positionRepository.findByName("Project Manager").orElseThrow(AssertionError::new);
        var positionEmployee = positionRepository.findByName("Developer").orElseThrow(AssertionError::new);
        var positionAdmin = positionRepository.findByName("Admin").orElseThrow(AssertionError::new);

        var admin = createAccount("admin@email.com", "admin", "admin@email.com", adminRole, positionAdmin);
        var mod = createAccount("mod@email.com", "moderator", "mod@email.com", userRole, positionManager);
        var user = createAccount("user@email.com", "user", "user@email.com", userRole, positionEmployee);


        accountRepository.save(admin);
        Assert.assertNotNull(admin.getId());

        accountRepository.save(mod);
        Assert.assertNotNull(mod.getId());

        accountRepository.save(user);
        Assert.assertNotNull(user.getId());

        var contractTypes = getContracts().get(0);
        var sex = getSexes().get(0);
        var country = getCountries().get(0);

        var adminDetails = createAccountDetails("Janusz", "Tracz", "Warszawska", "737 953 123", "30-123", admin,"Krakow", "123123123123", contractTypes, sex, country, "assets/icons/icon16.png");
        var modDetails = createAccountDetails("Krzysztof", "Andrusik", "Sławkowska", "556 781 905", "30-333",mod, "Limanowa", "456456456456", contractTypes, sex, country, "assets/icons/icon14.png");
        var userDetails = createAccountDetails("Helena", "Gwiazda", "Kopernika", "883 213 904", "30-634",user, "Przyszowa", "678678678678", contractTypes, sex, country, "assets/icons/icon4.png");

        accountDetailsRepository.save(adminDetails);
        accountDetailsRepository.save(modDetails);
        accountDetailsRepository.save(userDetails);

        var accounts = List.of(admin, mod, user);
        var roles = List.of(adminRole, userRole, userRole);

        var accountRoles = createAccountRoles(accounts, roles);

        accountRoleRepository.saveAllAndFlush(accountRoles);

        return accounts;

    }
    private List<Project> initProjects(List<Account> accounts){
        var project1 = Project.builder()
                .name("Volkswagen")
                .owner(accounts.get(0))
                .status(ProjectType.ACTIVE)
                .build();

        var project2 = Project.builder()
                .name("IKEA")
                .owner(accounts.get(1))
                .status(ProjectType.ACTIVE)
                .build();

        var project3 = Project.builder()
                .name("Starbucks")
                .owner(accounts.get(1))
                .status(ProjectType.ACTIVE)
                .build();

        var projects = List.of(project1, project2, project3);

        projectRepository.saveAll(projects);

        projects.forEach(project -> Assert.assertNotNull(project.getId()));

        var billingPeriods = getBillingPeriods ();

        var projectDetails1 = ProjectDetails.builder()
                .project(project1)
                .createdAt(new Date())
                .bonusModifier(1)
                .nightModifier(12)
                .holidayModifier(50)
                .imagePath("assets/companies/company1.png")
                .overtimeModifier(20)
                .billingPeriod(billingPeriods.get(0))
                .build();
        var projectDetails2 = ProjectDetails.builder()
                .project(project2)
                .createdAt(new Date())
                .billingPeriod(billingPeriods.get(0))
                .bonusModifier(1)
                .nightModifier(12)
                .holidayModifier(50)
                .imagePath("assets/companies/company2.png")
                .overtimeModifier(20)
                .build();
        var projectDetails3 = ProjectDetails.builder()
                .project(project3)
                .createdAt(new Date())
                .billingPeriod(billingPeriods.get(0))
                .bonusModifier(1)
                .nightModifier(12)
                .holidayModifier(50)
                .imagePath("assets/companies/company3.png")
                .overtimeModifier(20)
                .build();;

        projectDetailsRepository.saveAll(List.of(projectDetails1, projectDetails2, projectDetails3));

        return projects;
    }

    private List<BillingPeriod> getBillingPeriods() {
        var list = List.of(
                BillingPeriod.builder().name("Week").build(),
                BillingPeriod.builder().name("2 Weeks").build(),
                BillingPeriod.builder().name("Month").build(),
                BillingPeriod.builder().name("Season").build()
        );
        billingPeriodRepository.saveAll(list);

        return list;
    }

    private List<ContractType> getContracts() {

        var list = List.of(
                ContractType.builder().name("Employment contract").build(),
                ContractType.builder().name("Commission contract").build(),
                ContractType.builder().name("Specific-task contract").build(),
                ContractType.builder().name("B2B").build()
        );
        contractTypeRepository.saveAll(list);

        return list;
    }

    private List<Sex> getSexes() {
        var list = List.of(
                Sex.builder().name("Male").build(),
                Sex.builder().name("Female").build()
        );
        sexRepository.saveAll(list);

        return list;
    }

    private List<Country> getCountries() {
        List<Country> list = List.of(
                Country.builder()
                        .id(1234567)
                        .name("\uD83C\uDDF5\uD83C\uDDF1 Poland")
                        .build()
        );
        countryRepository.saveAll(list);

        return list;
    }

    private Account createAccount(String login, String pass, String email, Role role, Position position){

        return Account.builder()
                //.login(login)
                .pass(passwordEncoder.encode(pass))
                .email(email)
                .status(StatusType.ENABLE)
                .position(position)
                .build();
    }

    private AccountDetails createAccountDetails(String name, String surname, String street, String phoneNumber, String postalCode, Account account, String city, String pesel, ContractType contractType, Sex sex, Country country, String imagePath){

        return AccountDetails.builder()
                .account(account)
                .city(city)
                .pesel(pesel)
                .phoneNumber(phoneNumber)
                .street(street)
                .postalCode(postalCode)
                .taxNumber("22 4253 0000 3433 0642 0034")
                .sex(sex)
                .country(country)
                .surname(surname)
                .name(name)
                .city(city)
                .contractType(contractType)
                .imagePath(imagePath)
                //.billingPeriod(BillingPeriod.SEASON.label)
                .build();

    }

    private List<AccountRole> createAccountRoles(List<Account> accounts, List<Role> roles){
        List<AccountRole> accountRoles = new ArrayList<>();

        for (int i = 0; i < accounts.size(); i++) {
            var ar = AccountRole.builder()
                    .account(accounts.get(i))
                    .role(roles.get(i))
                    .build();

            accountRoles.add(ar);
        }

        return accountRoles;
    }

    private List<TaskForm> initTaskForms(List<Project> projects){
        List<TaskForm> taskForms = new ArrayList<>();

        for (Project project : projects) {
            taskForms.add(getTaskForm(project, "Programming"));
            taskForms.add(getTaskForm(project, "Design"));
            taskForms.add(getTaskForm(project, "Testing"));
        }

        taskFormRepository.saveAll(taskForms);

        List<TaskFormDetails> taskFormDetailsList = new ArrayList<>();

        taskForms.forEach(taskForm -> {
            taskFormDetailsList.add(
                    TaskFormDetails.builder()
                    .taskForm(taskForm)
                    .status(TaskFormType.OPEN)
                    .description("%s %s".formatted(taskForm.getName(), taskForm.getDescription()))
                    .build())
            ;
        });

        taskFormDetailsRepository.saveAll(taskFormDetailsList);

        return taskForms;
    }

    private TaskForm getTaskForm(Project project, String name) {
        return TaskForm.builder()
                .name(name)
                .project(project)
                .build();
    }

    private List<Task> initTasks(Account account, List<TaskForm> taskForms){
        List<Task> tasks = new ArrayList<>();
        var mapOfTaskStatusList = Map.of(
                "2022-02-04", List.of(TaskStatus.LOGGED, TaskStatus.LOGGED, TaskStatus.PENDING),
                "2023-02-03", List.of(TaskStatus.LOGGED, TaskStatus.LOGGED, TaskStatus.REJECTED),
                "2023-02-02", List.of(TaskStatus.LOGGED, TaskStatus.LOGGED, TaskStatus.APPROVED),
                "2023-02-01", List.of(TaskStatus.LOGGED, TaskStatus.LOGGED, TaskStatus.LOGGED),
                "2023-01-31", List.of(TaskStatus.APPROVED, TaskStatus.APPROVED, TaskStatus.APPROVED)
        );

        var temp =accountProjectRepository.findAllByAccountId(account.getId(), new PagingSettings().getPageable())
                .orElseThrow();

        var projects = temp
                .stream()
                .map(AccountProject::getProject)
                .toList();
        try {
            for (Project project : projects) {
                    tasks.addAll(generateTasks(account, taskForms, project, mapOfTaskStatusList));

            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

       return tasks;
    }

    private List<Task> generateTasks(Account account, List<TaskForm> taskForms, Project project, Map<String, List<TaskStatus>> mapOfTaskStatusList) throws ParseException {
        List<Task> tasks = new ArrayList<>();

        var filteredTaskForms = taskForms.stream()
                .filter(taskForm -> taskForm.getProject().getId().equals(project.getId()))
                .toList();

        for (TaskForm form : filteredTaskForms) {
            for (Map.Entry<String, List<TaskStatus>> entry : mapOfTaskStatusList.entrySet()) {
                var date = entry.getKey();
                var taskStatusList = entry.getValue();

                tasks.add(getTask(account, form, date + " 12:00:02", date + " 13:00:03", taskStatusList.get(0)));
                tasks.add(getTask(account, form, date + " 13:00:02", date + " 14:00:03", taskStatusList.get(1)));
                tasks.add(getTask(account, form, date + " 15:00:02", date + " 17:00:03", taskStatusList.get(2)));
            }
        }

        return tasks;
    }

    private Task getTask(Account account, TaskForm form, String dateFrom, String dateTo, TaskStatus status) throws ParseException {
        return Task
                .builder()
                .account(account)
                .name("task")
                .form(form)
                .dateFrom(sdf.parse(dateFrom))
                .dateTo(sdf.parse(dateTo))
                .status(status)
                .build();
    }


}