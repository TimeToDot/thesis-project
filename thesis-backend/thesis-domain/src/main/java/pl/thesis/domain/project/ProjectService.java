package pl.thesis.domain.project;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.thesis.data.account.AccountRepository;
import pl.thesis.data.account.model.Account;
import pl.thesis.data.account.model.StatusType;
import pl.thesis.data.position.PositionRepository;
import pl.thesis.data.project.AccountProjectRepository;
import pl.thesis.data.project.BillingPeriodRepository;
import pl.thesis.data.project.ProjectDetailsRepository;
import pl.thesis.data.project.ProjectRepository;
import pl.thesis.data.project.model.*;
import pl.thesis.data.role.RoleRepository;
import pl.thesis.data.role.model.Role;
import pl.thesis.data.role.model.RoleType;
import pl.thesis.data.task.TaskFormDetailsRepository;
import pl.thesis.data.task.TaskFormRepository;
import pl.thesis.data.task.TaskRepository;
import pl.thesis.data.task.model.TaskForm;
import pl.thesis.data.task.model.TaskFormDetails;
import pl.thesis.data.task.model.TaskFormType;
import pl.thesis.data.task.model.TaskStatus;
import pl.thesis.domain.employee.EmployeeService;
import pl.thesis.domain.employee.mapper.EmployeeTasksDTOMapper;
import pl.thesis.domain.employee.model.*;
import pl.thesis.domain.paging.PagingHelper;
import pl.thesis.domain.paging.PagingSettings;
import pl.thesis.domain.position.PositionMapperDTO;
import pl.thesis.domain.project.model.ProjectCreatePayloadDTO;
import pl.thesis.domain.project.model.ProjectDTO;
import pl.thesis.domain.project.model.ProjectUpdatePayloadDTO;
import pl.thesis.domain.project.model.ProjectsDTO;
import pl.thesis.domain.project.model.approval.*;
import pl.thesis.domain.project.model.employee.EmployeeDTO;
import pl.thesis.domain.project.model.employee.*;
import pl.thesis.domain.project.model.task.*;
import pl.thesis.domain.task.model.TaskStatusDTO;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

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
    private final PositionMapperDTO positionMapperDTO;
    private final BillingPeriodRepository billingPeriodRepository;
    private final EmployeeTasksDTOMapper employeeTasksDTOMapper;

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
    public UUID updateProject(ProjectUpdatePayloadDTO payloadDTO){
        var project = projectRepository.findById(payloadDTO.projectId()).orElseThrow();
        var projectDetails = projectDetailsRepository.findByProject(project).orElseThrow();
        var newOwner = accountRepository.findById(payloadDTO.moderatorId()).orElseThrow();
        var oldOwner = accountRepository.findById(project.getOwner().getId()).orElseThrow();
        ProjectType status;

        status = payloadDTO.active() ? ProjectType.ACTIVE : ProjectType.INACTIVE;
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
        var paging = PagingHelper.getPaging(settings, taskForms);
        var sorting = PagingHelper.getSorting(settings);
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
    public UUID addProjectTask(ProjectTaskCreatePayloadDTO payloadDTO){
        var project = projectRepository.findById(payloadDTO.projectId()).orElseThrow();

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
    public UUID updateProjectTask(ProjectTaskUpdatePayloadDTO payloadDTO){
        var project = projectRepository.findById(payloadDTO.projectId()).orElseThrow();
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

        var paging = PagingHelper.getPaging(settings, projectAccounts);
        var sorting = PagingHelper.getSorting(settings);

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

        var paging = PagingHelper.getPaging(settings, projectAccounts);
        var sorting = PagingHelper.getSorting(settings);

        return ProjectApprovalsDTO.builder()
                .employees(projectApprovalDTOList)
                .paging(paging)
                .sorting(sorting)
                .build();
    }

    @Transactional
    public void setProjectApprovalsEmployee(ProjectApprovalTasksPayloadDTO payloadDTO, PagingSettings settings){
        var project = projectRepository.findById(payloadDTO.projectId()).orElseThrow();
        var accountProject = accountProjectRepository.findById(payloadDTO.accountProjectId()).orElseThrow();
        var tasks = taskRepository.findByFormProjectIdAndAccountId(project.getId(), accountProject.getAccount().getId(), settings.getPageable()).orElseThrow();


        tasks.stream()
                .filter(task -> !payloadDTO.tasks().contains(task.getId()))
                .forEach(task -> task.setStatus(TaskStatus.APPROVED));

        taskRepository.saveAllAndFlush(tasks);

        if (!payloadDTO.tasks().isEmpty()) {
            var tasksToReject = payloadDTO.tasks().stream()
                    .map(taskRepository::findById)
                    .map(Optional::orElseThrow)
                    .filter(task -> task.getStatus().compareTo(TaskStatus.PENDING) == 0)
                    .collect(Collectors.toList());

            tasksToReject.forEach(task -> task.setStatus(TaskStatus.REJECTED));

            taskRepository.saveAllAndFlush(tasksToReject);
        }
    }

    @Transactional
    public UUID addProjectEmployee(ProjectEmployeeCreatePayloadDTO payloadDTO){
        var project = projectRepository.findById(payloadDTO.projectId()).orElseThrow();
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
    public UUID updateProjectEmployee(ProjectEmployeeUpdatePayloadDTO payloadDTO){
        var project = projectRepository.findById(payloadDTO.projectId()).orElseThrow();
        var accountProject = accountProjectRepository.findById(payloadDTO.projectEmployeeId()).orElseThrow();
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

    public CalendarDTO getProjectEmployeeCalendar(UUID projectEmployeeId, UUID projectId, Date date) {
        var projectAccount = accountProjectRepository.findById(projectEmployeeId).orElseThrow();

        return employeeService.getEmployeeCalendar(projectAccount.getAccount().getId(), projectId, date);
    }

    public EmployeeTasksDTO getProjectEmployeeTasks(UUID projectEmployeeId, UUID projectId, Date startDate, Date endDate, PagingSettings settings) {
        if (settings == null) {
            settings = new PagingSettings();
        }
        var accountProject = accountProjectRepository.findById(projectEmployeeId).orElseThrow();

        var tasks = taskRepository.findByAccountIdAndFormProjectIdAndDateFromBetween(accountProject.getAccount().getId(), projectId, startDate, endDate, settings.getPageable()).orElseThrow();

        var paging = PagingHelper.getPaging(settings, tasks);

        var sorting = PagingHelper.getSorting(settings);

        return EmployeeTasksDTO.builder()
                .tasks(employeeTasksDTOMapper.map(tasks.stream().toList()))
                .paging(paging)
                .sorting(sorting)
                .build();
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

    public ProjectCalendarDTO getProjectCalendarDTO(UUID id, UUID projectId, Date date) throws ParseException {
        var eId = employeeService.getEmployeeIdByProjectEmployee(id);
        var calendarDTO = employeeService.getEmployeeCalendar(eId, projectId, date);

        return ProjectCalendarDTO.builder()
                .employeeId(eId)
                .projectEmployeeId(id)
                .tasks(calendarDTO.tasks())
                .build();
    }

    private Map<Date, ApprovalStatus> getStatus(List<CalendarTaskDTO> calendarTaskDTOList) {

        List<CalendarTaskDTO> tasks;

        tasks = calendarTaskDTOList.stream()
                .filter(calendarTaskDTO -> calendarTaskDTO.status().compareTo(TaskStatusDTO.PENDING) == 0)
                .collect(Collectors.toList());
        if (!tasks.isEmpty()){
            if (tasks.size() > 1) {
                sort(tasks);
            }
            return Map.of(tasks.get(tasks.size() - 1).date(), ApprovalStatus.PENDING);
        }
        tasks = calendarTaskDTOList.stream()
                .filter(calendarTaskDTO -> calendarTaskDTO.status().compareTo(TaskStatusDTO.REJECTED) == 0)
                .collect(Collectors.toList());
        if (!tasks.isEmpty()){
            if (tasks.size() > 1) {
                sort(tasks);
            }
            return Map.of(tasks.get(tasks.size() - 1).date(), ApprovalStatus.REJECTED);
        }
        tasks = calendarTaskDTOList.stream()
                .filter(calendarTaskDTO -> calendarTaskDTO.status().compareTo(TaskStatusDTO.LOGGED) == 0)
                .collect(Collectors.toList());
        if (!tasks.isEmpty()){
            if (tasks.size() > 1) {
                sort(tasks);
            }

            return Map.of(tasks.get(tasks.size() - 1).date(), ApprovalStatus.LOGGED);
        }
        tasks = calendarTaskDTOList.stream()
                .filter(calendarTaskDTO -> calendarTaskDTO.status().compareTo(TaskStatusDTO.APPROVED) == 0)
                .collect(Collectors.toList());
        if (!tasks.isEmpty() && tasks.size() == calendarTaskDTOList.size()){
            if (tasks.size() > 1) {
                sort(tasks);
            }
            return Map.of(tasks.get(tasks.size() - 1).date(), ApprovalStatus.APPROVED);
        }

        return Collections.emptyMap();
        }

    private void sort(List<CalendarTaskDTO> tasks) {
        Collections.sort(tasks, new Comparator<CalendarTaskDTO>() {
            @Override
            public int compare(CalendarTaskDTO o1, CalendarTaskDTO o2) {
                return o1.date().compareTo(o2.date());
            }
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
                .position(position == null ? null : positionMapperDTO.simpleMap(position))
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
        //var jpaRole = List.of(role);

        var settings = PagingSettings.builder().page(page).size(size).build();
        var admins = accountRepository.findAllByRoleAndStatus(role, StatusType.ENABLE, settings.getPageable());
        var adminList = new ArrayList<>(admins.stream().toList());

        while (admins.getTotalPages() > page) {
            page += 1;
            settings = PagingSettings.builder().page(page).size(size).build();
            admins = accountRepository.findAllByRoleAndStatus(role, StatusType.ENABLE, settings.getPageable());
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
        // accountProjectRepository.delete(accountProject); currently not necessary
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
