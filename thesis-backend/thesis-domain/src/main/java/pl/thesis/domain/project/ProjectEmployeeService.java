package pl.thesis.domain.project;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.thesis.data.account.AccountRepository;
import pl.thesis.data.account.model.Account;
import pl.thesis.data.project.AccountProjectRepository;
import pl.thesis.data.project.ProjectRepository;
import pl.thesis.data.project.model.AccountProject;
import pl.thesis.data.project.model.Project;
import pl.thesis.data.project.model.ProjectAccountStatus;
import pl.thesis.data.role.RoleRepository;
import pl.thesis.data.role.model.RoleType;
import pl.thesis.data.task.TaskRepository;
import pl.thesis.data.task.model.TaskStatus;
import pl.thesis.domain.employee.EmployeeCalendarService;
import pl.thesis.domain.employee.mapper.EmployeeTasksDTOMapper;
import pl.thesis.domain.employee.model.CalendarDTO;
import pl.thesis.domain.employee.model.CalendarTaskDTO;
import pl.thesis.domain.employee.model.EmployeeTasksDTO;
import pl.thesis.domain.paging.PagingHelper;
import pl.thesis.domain.paging.PagingSettings;
import pl.thesis.domain.project.mapper.PEmployeeDTOMapper;
import pl.thesis.domain.project.mapper.ProjectEmployeeDTOMapper;
import pl.thesis.domain.project.model.approval.ApprovalStatus;
import pl.thesis.domain.project.model.approval.ProjectApprovalDTO;
import pl.thesis.domain.project.model.approval.ProjectApprovalTasksPayloadDTO;
import pl.thesis.domain.project.model.approval.ProjectApprovalsDTO;
import pl.thesis.domain.project.model.employee.*;
import pl.thesis.domain.task.model.TaskStatusDTO;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class ProjectEmployeeService {

    private final ProjectRepository projectRepository;
    private final AccountRepository accountRepository;
    private final AccountProjectRepository accountProjectRepository;
    private final TaskRepository taskRepository;
    private final EmployeeCalendarService employeeCalendarService;
    private final RoleRepository roleRepository;
    private final EmployeeTasksDTOMapper employeeTasksDTOMapper;
    private final PEmployeeDTOMapper employeeDTOMapper;
    private final ProjectEmployeeDTOMapper projectEmployeeDTOMapper;

    public ProjectEmployeeDTO getProjectEmployee(Long projectId, Long employeeId){
        var project = projectRepository.findById(projectId).orElseThrow();
        var accountProject = accountProjectRepository.findById(employeeId).orElseThrow();
        var account = accountRepository.findById(accountProject.getAccount().getId()).orElseThrow();

        var employeeDTO = getEmployeeDTO(account);

        return projectEmployeeDTOMapper.map(accountProject, employeeDTO);
    }

    @Transactional
    public ProjectEmployeesDTO getProjectEmployees(Long projectId, Boolean active, PagingSettings settings){
        var project = projectRepository.findById(projectId).orElseThrow();
        var status = Boolean.TRUE.equals(active) ? ProjectAccountStatus.ACTIVE : ProjectAccountStatus.INACTIVE;

        var projectAccounts = accountProjectRepository.findAllByProjectIdAndStatus(project.getId(), status, settings.getPageable()).orElseThrow();

        var paging = PagingHelper.getPaging(settings, projectAccounts);
        var sorting = PagingHelper.getSorting(settings);

        var employees = projectAccounts.stream()
                .map(accountProject -> projectEmployeeDTOMapper.map(
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
    public ProjectApprovalsDTO getProjectApprovalEmployees(Long projectId, PagingSettings settings){
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
    public Long addProjectEmployee(ProjectEmployeeCreatePayloadDTO payloadDTO){
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
    public Long updateProjectEmployee(ProjectEmployeeUpdatePayloadDTO payloadDTO){
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

    public CalendarDTO getProjectEmployeeCalendar(Long projectEmployeeId, Long projectId, Date date) {
        var projectAccount = accountProjectRepository.findById(projectEmployeeId).orElseThrow();

        return employeeCalendarService.getEmployeeCalendar(projectAccount.getAccount().getId(), projectId, date);
    }

    public EmployeeTasksDTO getProjectEmployeeTasks(Long projectEmployeeId, Long projectId, Date startDate, Date endDate, PagingSettings settings) {
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



    private EmployeeDTO getEmployeeDTO(Account accountTemp) {
        var account = accountRepository.findById(accountTemp.getId()).orElseThrow();

        return employeeDTOMapper.map(account.getDetails());
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

        var calendar = employeeCalendarService.getCalendarTaskDTOList(taskList);
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

    // TODO: refactor needed
    private Map<Date, ApprovalStatus> getStatus(List<CalendarTaskDTO> calendarTaskDTOList) {

        var statusMapping = Map.of(
                TaskStatusDTO.PENDING, ApprovalStatus.PENDING,
                TaskStatusDTO.REJECTED, ApprovalStatus.REJECTED,
                TaskStatusDTO.LOGGED, ApprovalStatus.LOGGED,
                TaskStatusDTO.APPROVED, ApprovalStatus.APPROVED
        );

        return calendarTaskDTOList.stream()
                .filter(calendarTaskDTO -> statusMapping.containsKey(calendarTaskDTO.status()))
                .collect(Collectors.groupingBy(
                        CalendarTaskDTO::date,
                        Collectors.mapping(
                                calendarTaskDTO -> statusMapping.get(calendarTaskDTO.status()),
                                Collectors.toList()
                        )
                ))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue()
                                .stream()
                                .max(Comparator.naturalOrder())
                                .orElseThrow()
                ));
    }
}
