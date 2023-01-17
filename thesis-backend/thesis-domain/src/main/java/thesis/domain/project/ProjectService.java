package thesis.domain.project;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thesis.data.account.AccountRepository;
import thesis.data.account.model.Account;
import thesis.data.account.model.StatusType;
import thesis.data.project.AccountProjectRepository;
import thesis.data.project.ProjectRepository;
import thesis.data.project.model.AccountProject;
import thesis.data.project.model.Project;
import thesis.data.project.model.ProjectAccountStatus;
import thesis.data.project.model.ProjectType;
import thesis.data.task.TaskFormRepository;
import thesis.data.task.TaskRepository;
import thesis.data.task.model.TaskForm;
import thesis.data.task.model.TaskFormType;
import thesis.data.task.model.TaskStatus;
import thesis.domain.employee.EmployeeService;
import thesis.domain.employee.model.CalendarTaskDTO;
import thesis.domain.employee.model.ContractTypeDTO;
import thesis.domain.paging.PagingHelper;
import thesis.domain.paging.PagingSettings;
import thesis.domain.project.model.ProjectDTO;
import thesis.domain.project.model.ProjectsDTO;
import thesis.domain.project.model.approval.ApprovalStatus;
import thesis.domain.project.model.approval.ProjectApprovalDTO;
import thesis.domain.project.model.approval.ProjectApprovalsDTO;
import thesis.domain.project.model.employee.EmployeeDTO;
import thesis.domain.project.model.employee.ProjectEmployeeDTO;
import thesis.domain.project.model.employee.ProjectEmployeesDTO;
import thesis.domain.project.model.task.ProjectTaskDTO;
import thesis.domain.project.model.task.ProjectTaskDetailsDTO;
import thesis.domain.project.model.task.ProjectTasksDTO;
import thesis.domain.project.model.task.ProjectTasksDetailsDTO;
import thesis.domain.task.model.TaskStatusDTO;

import java.util.*;
import java.util.stream.Collectors;

import static thesis.domain.paging.PagingHelper.*;

@Slf4j
@AllArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final AccountRepository accountRepository;
    private final AccountProjectRepository accountProjectRepository;
    private final TaskFormRepository taskFormRepository;
    private final TaskRepository taskRepository;
    private final EmployeeService employeeService;

    public ProjectDTO getProject(UUID projectId){
        var project = projectRepository.findById(projectId).orElseThrow();
        return getProjectDTO(project);
    }

    public ProjectsDTO getProjects(Boolean active){
        var projects = projectRepository.findAllByStatus(active ? ProjectType.ACTIVE : ProjectType.INACTIVE).orElseThrow();

        return ProjectsDTO.builder()
                .project(projects.stream().map(this::getProjectDTO).toList())
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
        var taskFormStatus = status ? TaskFormType.OPEN : TaskFormType.CLOSE;

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
        var taskFormStatus = status ? TaskFormType.OPEN : TaskFormType.CLOSE;
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

    public ProjectEmployeeDTO getProjectEmployee(UUID projectId, UUID employeeId){
        var account = accountRepository.findById(employeeId).orElseThrow();
        var project = projectRepository.findById(projectId).orElseThrow();
        var accountProject = accountProjectRepository.findByAccountIdAndProjectId(account.getId(), project.getId()).orElseThrow();

        var employeeDTO = getEmployeeDTO(account);

        return getProjectEmployeeDTO(accountProject, employeeDTO);
    }

    public ProjectEmployeesDTO getProjectEmployees(UUID projectId, Boolean active, PagingSettings settings){
        var project = projectRepository.findById(projectId).orElseThrow();
        var status = active ? ProjectAccountStatus.ACTIVE : ProjectAccountStatus.INACTIVE;

        var projectAccounts = accountProjectRepository.findAllByProjectIdAndStatus(project.getId(), status, settings.getPageable()).orElseThrow();

        var paging = getPaging(settings, projectAccounts);
        var sorting = getSorting(settings);

        var employees = projectAccounts.stream()
                .map(accountProject -> getProjectEmployeeDTO(accountProject, getEmployeeDTO(accountProject.getAccount())))
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
            addToProjectApprovalList(project, projectApprovalDTOList, accountProject);
        }

        var paging = getPaging(settings, projectAccounts);
        var sorting = getSorting(settings);

        return ProjectApprovalsDTO.builder()
                .employees(projectApprovalDTOList)
                .paging(paging)
                .sorting(sorting)
                .build();
    }

    private void addToProjectApprovalList(Project project, List<ProjectApprovalDTO> projectApprovalDTOList, AccountProject accountProject) {
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

        if (map == null){
            date = null;
            status = ApprovalStatus.NONE;
        } else {
            date = map.keySet().stream().toList().get(0);
            status = map.values().stream().toList().get(0);
        }

        var approval = ProjectApprovalDTO.builder()
                .projectEmployeeId(accountProject.getId())
                .employee(getEmployeeDTO(accountProject.getAccount()))
                .status(status)
                .lastRequest(date)
                .build();

        projectApprovalDTOList.add(approval);
    }

    private Map<Date, ApprovalStatus> getStatus(List<CalendarTaskDTO> calendarTaskDTOList) {

        List<CalendarTaskDTO> tasks;

        tasks = calendarTaskDTOList.stream()
                .filter(calendarTaskDTO -> calendarTaskDTO.status().compareTo(TaskStatusDTO.PENDING) == 0)
                .toList();
        if (!tasks.isEmpty()){
            sort(tasks);
            return Map.of(tasks.get(tasks.size() - 1).date(), ApprovalStatus.PENDING);
        }
        tasks = calendarTaskDTOList.stream()
                .filter(calendarTaskDTO -> calendarTaskDTO.status().compareTo(TaskStatusDTO.REJECTED) == 0)
                .toList();
        if (!tasks.isEmpty()){
            sort(tasks);
            return Map.of(tasks.get(tasks.size() - 1).date(), ApprovalStatus.REJECTED);
        }
        tasks = calendarTaskDTOList.stream()
                .filter(calendarTaskDTO -> calendarTaskDTO.status().compareTo(TaskStatusDTO.LOGGED) == 0)
                .toList();
        if (!tasks.isEmpty()){
            sort(tasks);
            return Map.of(tasks.get(tasks.size() - 1).date(), ApprovalStatus.LOGGED);
        }
        tasks = calendarTaskDTOList.stream()
                .filter(calendarTaskDTO -> calendarTaskDTO.status().compareTo(TaskStatusDTO.APPROVED) == 0)
                .toList();
        if (!tasks.isEmpty() && tasks.size() == calendarTaskDTOList.size()){
            sort(tasks);
            return Map.of(tasks.get(tasks.size() - 1).date(), ApprovalStatus.APPROVED);
        }

        return null;
        }

    private void sort(List<CalendarTaskDTO> tasks) {
        Collections.sort(tasks,
                (o1, o2) -> {
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

    private EmployeeDTO getEmployeeDTO(Account account) {
        return EmployeeDTO.builder()
                .employeeId(account.getId())
                .firstName(account.getDetails().getName())
                .lastName(account.getDetails().getSurname())
                .email(account.getEmail())
                .imagePath(account.getDetails().getImagePath())
                .position(account.getPosition().getId())
                .employmentDate(account.getDetails().getEmploymentDate())
                .contractTypeDTO(ContractTypeDTO.fromValue(account.getDetails().getContractType()))
                .wage(account.getDetails().getWage())
                .workingTime(account.getDetails().getWorkingTime())
                .active(account.getStatus().compareTo(StatusType.ENABLE) == 0)
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

    private ProjectDTO getProjectDTO(Project project) {
        var accountsNumber = project.getAccountProjects().stream().map(AccountProject::getAccount).toList().size();

        return ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .billingPeriod(project.getDetails().getBillingPeriod())
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
}
