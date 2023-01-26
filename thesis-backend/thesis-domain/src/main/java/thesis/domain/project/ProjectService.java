package thesis.domain.project;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thesis.data.account.AccountRepository;
import thesis.data.account.model.Account;
import thesis.data.account.model.StatusType;
import thesis.data.position.PositionRepository;
import thesis.data.project.AccountProjectRepository;
import thesis.data.project.BillingPeriodRepository;
import thesis.data.project.ProjectDetailsRepository;
import thesis.data.project.ProjectRepository;
import thesis.data.project.model.*;
import thesis.data.role.RoleRepository;
import thesis.data.role.model.Role;
import thesis.data.role.model.RoleType;
import thesis.data.task.TaskFormDetailsRepository;
import thesis.data.task.TaskFormRepository;
import thesis.data.task.TaskRepository;
import thesis.data.task.model.TaskForm;
import thesis.data.task.model.TaskFormDetails;
import thesis.data.task.model.TaskFormType;
import thesis.domain.employee.EmployeeService;
import thesis.domain.employee.model.BillingPeriodDTO;
import thesis.domain.employee.model.CalendarTaskDTO;
import thesis.domain.paging.PagingSettings;
import thesis.domain.position.PositionMapper;
import thesis.domain.project.model.ProjectCreatePayloadDTO;
import thesis.domain.project.model.ProjectDTO;
import thesis.domain.project.model.ProjectUpdatePayloadDTO;
import thesis.domain.project.model.ProjectsDTO;
import thesis.domain.project.model.approval.ApprovalStatus;
import thesis.domain.project.model.approval.ProjectApprovalDTO;
import thesis.domain.project.model.approval.ProjectApprovalsDTO;
import thesis.domain.project.model.employee.*;
import thesis.domain.project.model.task.*;
import thesis.domain.task.model.TaskStatusDTO;
import thesis.security.services.model.ContractDTO;
import thesis.security.services.model.ContractTypeDTO;

import java.util.*;

import static thesis.domain.paging.PagingHelper.*;

@Slf4j
@AllArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectDetailsRepository projectDetailsRepository;
    private final AccountRepository accountRepository;
    private final AccountProjectRepository accountProjectRepository;
    private final TaskFormRepository taskFormRepository;
    private final TaskFormDetailsRepository taskFormDetailsRepository;
    private final TaskRepository taskRepository;
    private final EmployeeService employeeService;
    private final RoleRepository roleRepository;
    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;
    private final BillingPeriodRepository billingPeriodRepository;

    public ProjectDTO getProject(UUID projectId){
        var project = projectRepository.findById(projectId).orElseThrow();
        return getProjectDTO(project);
    }

    @Transactional
    public UUID addProject(ProjectCreatePayloadDTO payloadDTO){
        var owner = accountRepository.findById(payloadDTO.moderatorId()).orElseThrow();
        var project = getProject(payloadDTO, owner);

        projectRepository.save(project);

        var projectDetails = getProjectDetails(payloadDTO, project);

        projectDetailsRepository.save(projectDetails);

        var projectAdminRole = roleRepository.findByName(RoleType.ROLE_PROJECT_ADMIN).orElseThrow();
        var projectModRole = roleRepository.findByName(RoleType.ROLE_PROJECT_MODERATOR).orElseThrow();
        var adminList = getAdmins();

        assignToProject(project, projectAdminRole, adminList);
        assignToProject(project, projectModRole, List.of(owner));

        return project.getId();
    }

    @Transactional
    public UUID updateProject(UUID id, ProjectUpdatePayloadDTO payloadDTO){
        var project = projectRepository.findById(id).orElseThrow();
        var projectDetails = projectDetailsRepository.findByProject(project).orElseThrow();
        var newOwner = accountRepository.findById(payloadDTO.moderatorId()).orElseThrow();
        var oldOwner = accountRepository.findById(project.getOwner().getId()).orElseThrow();
        ProjectType status;

        status = payloadDTO.active()? ProjectType.ACTIVE : ProjectType.INACTIVE;
        boolean isPermissionChangeNeeded = project.getOwner().getId().compareTo(newOwner.getId()) != 0;

        project.setOwner(newOwner);
        project.setName(payloadDTO.name());
        project.setDescription(payloadDTO.description());
        project.setStatus(status);

        projectRepository.save(project);

        projectDetails.setBillingPeriod(
                BillingPeriod.builder()
                        .id(payloadDTO.billingPeriod().id())
                        .name(payloadDTO.name())
                        .build()
        );
        projectDetails.setBonusModifier(payloadDTO.bonusModifier());
        projectDetails.setOvertimeModifier(payloadDTO.overtimeModifier());
        projectDetails.setBonusModifier(payloadDTO.bonusModifier());
        projectDetails.setHolidayModifier(payloadDTO.holidayModifier());
        projectDetails.setNightModifier(payloadDTO.nightModifier());
        if (status.compareTo(ProjectType.INACTIVE) == 0) projectDetails.setArchiveDate(new Date());

        projectDetailsRepository.save(projectDetails);

        if (isPermissionChangeNeeded){
            changeOwners(project, newOwner, oldOwner);
        }

        return project.getId();
    }

    public ProjectsDTO getProjects(Boolean active){
        var status = Boolean.TRUE.equals(active) ? ProjectType.ACTIVE : ProjectType.INACTIVE;

        var projects = projectRepository.findAllByStatus(status).orElseThrow();
        //paging for production needed

        return ProjectsDTO.builder()
                .projects(projects.stream().map(this::getProjectDTO).toList())
                .build();
    }

    public ProjectTaskDetailsDTO getProjectTask(UUID projectId, UUID taskId){
        var taskForm = taskFormRepository.findById(taskId).orElseThrow();

        if (taskForm.getProject().getId().compareTo(projectId) != 0){
            throw new RuntimeException("This task " + taskId + "is not from project: " + projectId);
        }

        return ProjectTaskDetailsDTO.builder()
                .id(taskForm.getId())
                .name(taskForm.getName())
                .description(taskForm.getDescription())
                .creationDate(taskForm.getCreatedAt())
                .archiveDate(taskForm.getArchiveDate())
                .projectId(taskForm.getId())
                .active(taskForm.getDetails().getStatus().compareTo(TaskFormType.OPEN) == 0)
                .build();

    }

    public ProjectTasksDetailsDTO getAdvancedProjectTasks(UUID projectId, Boolean status, PagingSettings settings){
        var taskFormStatus = Boolean.TRUE.equals(status) ? TaskFormType.OPEN : TaskFormType.CLOSE;

        var taskForms = taskFormRepository.findAllByProjectIdAndDetailsStatus(projectId, taskFormStatus, settings.getPageable());
        var paging = getPaging(settings, taskForms);
        var sorting = getSorting(settings);
        var taskFormList = taskForms.stream()
                .map(this::getAdvancedProjectTaskDTO)
                .toList();

        return ProjectTasksDetailsDTO.builder()
                .tasks(taskFormList)
                .sorting(sorting)
                .paging(paging)
                .build();

    }

    public ProjectTasksDTO getProjectTasks(UUID projectId, Boolean status){
        var taskFormStatus = Boolean.TRUE.equals(status) ? TaskFormType.OPEN : TaskFormType.CLOSE;
        var size = 100;
        var page = 1;
        var settings = PagingSettings.builder().page(page).size(size).build();
        var taskForms = taskFormRepository.findAllByProjectIdAndDetailsStatus(projectId, taskFormStatus, settings.getPageable());
        var tempTaskForms = new ArrayList<>(taskForms.stream().toList());

        while (taskForms.getTotalPages() > page) {
            page += 1;
            settings = PagingSettings.builder().page(page).size(size).build();
            taskForms = taskFormRepository.findAllByProjectIdAndDetailsStatus(projectId, taskFormStatus, settings.getPageable());
            tempTaskForms.addAll(taskForms.stream().toList());
        }

        var taskFormList = tempTaskForms.stream()
                .map(this::getAdvancedProjectTaskDTO)
                .toList();

        return ProjectTasksDTO.builder()
                .tasks(taskFormList)
                .build();
    }

    @Transactional
    public UUID addProjectTask(UUID projectId, ProjectTaskCreatePayloadDTO payloadDTO){
        var project = projectRepository.findById(projectId).orElseThrow();

        var taskForm = TaskForm.builder()
                .project(project)
                .name(payloadDTO.name())
                .description(payloadDTO.description())
                .createdAt(new Date())
                .build();

        taskFormRepository.save(taskForm);

        var taskFormDetails = TaskFormDetails.builder()
                .taskForm(taskForm)
                .status(TaskFormType.OPEN)
                .build();

        taskFormDetailsRepository.save(taskFormDetails);

        return taskForm.getId();
    }

    @Transactional
    public UUID updateProjectTask(UUID projectId, ProjectTaskUpdatePayloadDTO payloadDTO){
        var project = projectRepository.findById(projectId).orElseThrow();
        var taskForm = taskFormRepository.findById(payloadDTO.id()).orElseThrow();

        taskForm.setName(payloadDTO.name());
        taskForm.setDescription(payloadDTO.description());
        var status = Boolean.TRUE.equals(payloadDTO.active()) ? TaskFormType.OPEN : TaskFormType.CLOSE;
        taskForm.getDetails().setStatus(status);

        taskFormRepository.save(taskForm);

        return taskForm.getId();
    }

    public ProjectEmployeeDTO getProjectEmployee(UUID projectId, UUID employeeId){
        var project = projectRepository.findById(projectId).orElseThrow();
        var accountProject = accountProjectRepository.findById(employeeId).orElseThrow();
        var account = accountRepository.findById(accountProject.getAccount().getId()).orElseThrow();

        var employeeDTO = getEmployeeDTO(account);

        return getProjectEmployeeDTO(accountProject, employeeDTO);
    }

    @Transactional
    public ProjectEmployeesDTO getProjectEmployees(UUID projectId, Boolean active, PagingSettings settings){
        var project = projectRepository.findById(projectId).orElseThrow();
        var status = Boolean.TRUE.equals(active) ? ProjectAccountStatus.ACTIVE : ProjectAccountStatus.INACTIVE;

        var projectAccounts = accountProjectRepository.findAllByProjectIdAndStatus(project.getId(), status, settings.getPageable()).orElseThrow();

        var paging = getPaging(settings, projectAccounts);
        var sorting = getSorting(settings);

        var employees = projectAccounts.stream()
                .map(accountProject -> getProjectEmployeeDTO(
                        accountProject,
                        getEmployeeDTO(accountProject.getAccount())))
                .toList();

        return ProjectEmployeesDTO.builder()
                .employees(employees)
                .sorting(sorting)
                .paging(paging)
                .build();
    }

    @Transactional
    public ProjectApprovalsDTO getProjectApprovalEmployees(UUID projectId, PagingSettings settings){
        var project = projectRepository.findById(projectId).orElseThrow();
        var projectAccounts = accountProjectRepository.findAllByProjectIdAndStatus(project.getId(), ProjectAccountStatus.ACTIVE, settings.getPageable()).orElseThrow();
        List<ProjectApprovalDTO> projectApprovalDTOList = new ArrayList<>();

        for (AccountProject accountProject : projectAccounts) {
            var projectApprovalDTO = getProjectApprovalDTO(project, accountProject);
            projectApprovalDTOList.add(projectApprovalDTO);
        }

        var paging = getPaging(settings, projectAccounts);
        var sorting = getSorting(settings);

        return ProjectApprovalsDTO.builder()
                .employees(projectApprovalDTOList)
                .paging(paging)
                .sorting(sorting)
                .build();
    }

    @Transactional
    public UUID addProjectEmployee(UUID projectId, ProjectEmployeeCreatePayloadDTO payloadDTO){
        var project = projectRepository.findById(projectId).orElseThrow();
        var account = accountRepository.findById(payloadDTO.employeeId()).orElseThrow();
        var roleType = RoleType.ROLE_PROJECT_USER;
        var role = roleRepository.findByName(roleType).orElseThrow();

        var accountProject = AccountProject.builder()
                .project(project)
                .account(account)
                .modifier(payloadDTO.modifier())
                .joinDate(new Date())
                .workingTime(payloadDTO.workingTime())
                .status(ProjectAccountStatus.ACTIVE)
                .role(role)
                .build();

        accountProjectRepository.save(accountProject);

        return accountProject.getId();
    }

    @Transactional
    public UUID updateProjectEmployee(UUID projectId, UUID employeeProjectId, ProjectEmployeeUpdatePayloadDTO payloadDTO){
        var project = projectRepository.findById(projectId).orElseThrow();
        var accountProject = accountProjectRepository.findById(employeeProjectId).orElseThrow();
        var status = Boolean.TRUE.equals(payloadDTO.active()) ? ProjectAccountStatus.ACTIVE : ProjectAccountStatus.INACTIVE;
        var roleType = RoleType.ROLE_PROJECT_USER;
        var role = roleRepository.findByName(roleType).orElseThrow();

        accountProject.setStatus(status);
        accountProject.setModifier(payloadDTO.modifier());
        accountProject.setWorkingTime(payloadDTO.workingTime());
        accountProject.setRole(role);

        accountProjectRepository.save(accountProject);

        return accountProject.getId();
    }

    public List<BillingPeriodDTO> getBillingPeriods(){
        var billingPeriods = billingPeriodRepository.findAll();

        return billingPeriods.stream()
                .map(this::getBillingPeriodDTO)
                .toList();
    }

    private ProjectApprovalDTO getProjectApprovalDTO(Project project, AccountProject accountProject) {
        var size = 100;
        var page = 1;
        var internalSettings = PagingSettings.builder().page(page).size(size).build();
        var tasks = taskRepository.findByFormProjectIdAndAccountId(project.getId(), accountProject.getAccount().getId(), internalSettings.getPageable()).orElseThrow();
        var taskList = new ArrayList<>(tasks.stream().toList());

        while (tasks.getTotalPages() > page) {
            page += 1;
            internalSettings = PagingSettings.builder().page(page).size(size).build();
            tasks = taskRepository.findByFormProjectIdAndAccountId(project.getId(), accountProject.getAccount().getId(), internalSettings.getPageable()).orElseThrow();
            taskList.addAll(tasks.stream().toList());
        }

        var calendar = employeeService.getCalendarTaskDTOList(taskList);
        var map = getStatus(calendar);
        Date date;
        ApprovalStatus status;

        if (map.isEmpty()){
            date = null;
            status = ApprovalStatus.NONE;
        } else {
            date = map.keySet().stream().toList().get(0);
            status = ApprovalStatus.valueOf(map.values().stream().toList().get(0).name());
        }

        return ProjectApprovalDTO.builder()
                .projectEmployeeId(accountProject.getId())
                .employee(getEmployeeDTO(accountProject.getAccount()))
                .status(status)
                .lastRequest(date)
                .build();
    }

    private Map<Date, ApprovalStatus> getStatus(List<CalendarTaskDTO> calendarTaskDTOList) {

        List<CalendarTaskDTO> tasks;

        tasks = calendarTaskDTOList.stream()
                .filter(calendarTaskDTO -> calendarTaskDTO.status().compareTo(TaskStatusDTO.PENDING) == 0)
                .toList();
        if (!tasks.isEmpty()){
            if (tasks.size() > 1) {
                sort(tasks);
            }
            return Map.of(tasks.get(tasks.size() - 1).date(), ApprovalStatus.PENDING);
        }
        tasks = calendarTaskDTOList.stream()
                .filter(calendarTaskDTO -> calendarTaskDTO.status().compareTo(TaskStatusDTO.REJECTED) == 0)
                .toList();
        if (!tasks.isEmpty()){
            if (tasks.size() > 1) {
                sort(tasks);
            }
            return Map.of(tasks.get(tasks.size() - 1).date(), ApprovalStatus.REJECTED);
        }
        tasks = calendarTaskDTOList.stream()
                .filter(calendarTaskDTO -> calendarTaskDTO.status().compareTo(TaskStatusDTO.LOGGED) == 0)
                .toList();
        if (!tasks.isEmpty()){
            if (tasks.size() > 1) {
                sort(tasks);
            }

            return Map.of(tasks.get(tasks.size() - 1).date(), ApprovalStatus.LOGGED);
        }
        tasks = calendarTaskDTOList.stream()
                .filter(calendarTaskDTO -> calendarTaskDTO.status().compareTo(TaskStatusDTO.APPROVED) == 0)
                .toList();
        if (!tasks.isEmpty() && tasks.size() == calendarTaskDTOList.size()){
            if (tasks.size() > 1) {
                sort(tasks);
            }
            return Map.of(tasks.get(tasks.size() - 1).date(), ApprovalStatus.APPROVED);
        }

        return Collections.emptyMap();
        }

    private void sort(List<CalendarTaskDTO> tasks) {
        tasks.sort((o1, o2) -> {
            if (o1.date() == null || o2.date() == null)
                return 0;
            return o1.date().compareTo(o2.date());
        });
    }

    private ProjectEmployeeDTO getProjectEmployeeDTO(AccountProject accountProject, EmployeeDTO employeeDTO) {
        return ProjectEmployeeDTO.builder()
                .projectEmployeeId(accountProject.getId())
                .employee(employeeDTO)
                .active(accountProject.getStatus().compareTo(ProjectAccountStatus.ACTIVE) == 0)
                .joinDate(accountProject.getJoinDate())
                .exitDate(accountProject.getExitDate())
                .workingTime(accountProject.getWorkingTime())
                .build();
    }

    private EmployeeDTO getEmployeeDTO(Account accountTemp) {
        var account = accountRepository.findById(accountTemp.getId()).orElseThrow();
        var position = account.getPosition();

        return EmployeeDTO.builder()
                .employeeId(account.getId())
                .firstName(account.getDetails().getName())
                .lastName(account.getDetails().getSurname())
                .email(account.getEmail())
                .imagePath(account.getDetails().getImagePath())
                .position(position == null ? null : positionMapper.simpleMap(position))
                .employmentDate(account.getDetails().getEmploymentDate())
                .contractType(
                        getContractTypeDTO(account)
                )
                .wage(account.getDetails().getWage())
                .workingTime(account.getDetails().getWorkingTime())
                .active(account.getStatus().compareTo(StatusType.ENABLE) == 0)
                .build();
    }

    private ContractDTO getContractTypeDTO(Account account) {
        return ContractDTO.builder()
                .id(account.getDetails().getContractType().getId())
                .name(account.getDetails().getContractType().getName())
                .build();
    }

    private ProjectTaskDetailsDTO getAdvancedProjectTaskDTO(TaskForm taskForm) {
        return ProjectTaskDetailsDTO.builder()
                .id(taskForm.getId())
                .name(taskForm.getName())
                .description(taskForm.getDescription())
                .creationDate(taskForm.getCreatedAt())
                .archiveDate(taskForm.getArchiveDate())
                .projectId(taskForm.getProject().getId())
                .build();
    }

    private ProjectTaskDTO getProjectTaskDTO(TaskForm taskForm) {
        return ProjectTaskDTO.builder()
                .id(taskForm.getId())
                .projectId(taskForm.getProject().getId())
                .name(taskForm.getName())
                .build();
    }

    private ProjectDTO getProjectDTO(Project projectTemp) {
        var project = projectRepository.findById(projectTemp.getId()).orElseThrow();
        var accountsNumber = project.getAccountProjects().stream().map(AccountProject::getAccount).toList().size();
        var billingPeriod = project.getDetails().getBillingPeriod();

        return ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .billingPeriod(
                        getBillingPeriodDTO(billingPeriod)
                )
                .overtimeModifier(project.getDetails().getOvertimeModifier())
                .bonusModifier(project.getDetails().getBonusModifier())
                .nightModifier(project.getDetails().getNightModifier())
                .holidayModifier(project.getDetails().getHolidayModifier())
                .image(project.getDetails().getImagePath())
                .moderatorId(project.getOwner().getId())
                .employeesCount(accountsNumber)
                .active(project.getStatus().compareTo(ProjectType.ACTIVE) == 0)
                .build();
    }

    private void assignToProject(Project project, Role projectRole, List<Account> adminList) {
        adminList.forEach(account -> {
            var adminProject = AccountProject.builder()
                    .account(account)
                    .project(project)
                    .role(projectRole)
                    .joinDate(new Date())
                    .status(ProjectAccountStatus.ACTIVE)
                    .build();

            accountProjectRepository.save(adminProject);
        });
    }

    private ArrayList<Account> getAdmins() {
        var size = 100;
        var page = 1;
        var role = roleRepository.findByName(RoleType.ROLE_GLOBAL_ADMIN).orElseThrow();
        var jpaRole = List.of(List.of(role));

        var settings = PagingSettings.builder().page(page).size(size).build();
        var admins = accountRepository.findAllByRolesInAndStatus(jpaRole, StatusType.ENABLE, settings.getPageable());
        var adminList = new ArrayList<>(admins.stream().toList());

        while (admins.getTotalPages() > page) {
            page += 1;
            settings = PagingSettings.builder().page(page).size(size).build();
            admins = accountRepository.findAllByRolesInAndStatus(jpaRole, StatusType.ENABLE, settings.getPageable());
            adminList.addAll(admins.stream().toList());
        }

        return adminList;
    }

    private Project getProject(ProjectCreatePayloadDTO payloadDTO, Account owner) {
        return Project.builder()
                .name(payloadDTO.name())
                .description(payloadDTO.description())
                .owner(owner)
                .status(ProjectType.ACTIVE)
                .build();
    }

    private ProjectDetails getProjectDetails(ProjectCreatePayloadDTO payloadDTO, Project project) {
        var billingPeriod = billingPeriodRepository.findById(payloadDTO.billingPeriod().id()).orElseThrow();

        return ProjectDetails.builder()
                .project(project)
                .createdAt(new Date())
                .imagePath(payloadDTO.image())
                .billingPeriod(billingPeriod)
                .overtimeModifier(payloadDTO.overtimeModifier())
                .bonusModifier(payloadDTO.bonusModifier())
                .holidayModifier(payloadDTO.holidayModifier())
                .nightModifier(payloadDTO.nightModifier())
                .build();
    }

    private BillingPeriodDTO getBillingPeriodDTO(BillingPeriod billingPeriod) {
        return BillingPeriodDTO.builder()
                .id(billingPeriod.getId())
                .name(billingPeriod.getName())
                .build();
    }

    private void changeOwners(Project project, Account newOwner, Account oldOwner) {
        var accountProject = accountProjectRepository.findByAccountIdAndProjectId(oldOwner.getId(), project.getId()).orElseThrow();
        var projectModRole = roleRepository.findByName(RoleType.ROLE_PROJECT_MODERATOR).orElseThrow();

        accountProject.setStatus(ProjectAccountStatus.EXPIRED_MOD);
        // accountProjectRepository.delete(accountProject); not curently not necessary
        accountProjectRepository.save(accountProject);

        var newAccountProject = AccountProject.builder()
                .project(project)
                .account(newOwner)
                .joinDate(new Date())
                .status(ProjectAccountStatus.MOD)
                .role(projectModRole)
                .build();

        accountProjectRepository.save(newAccountProject);
    }
}
