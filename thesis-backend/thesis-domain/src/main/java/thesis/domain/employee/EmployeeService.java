package thesis.domain.employee;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thesis.data.account.AccountDetailsRepository;
import thesis.data.account.AccountRepository;
import thesis.data.project.ProjectAccountRepository;
import thesis.data.project.ProjectRepository;
import thesis.data.project.model.ProjectAccount;
import thesis.data.task.TaskFormRepository;
import thesis.data.task.TaskRepository;
import thesis.data.task.model.Task;
import thesis.data.task.model.TaskStatus;
import thesis.domain.employee.mapper.EmployeeTaskDTOMapper;
import thesis.domain.employee.mapper.EmployeeTasksDTOMapper;
import thesis.domain.employee.model.*;
import thesis.domain.paging.PagingSettings;
import thesis.domain.employee.mapper.EmployeeDTOMapper;
import thesis.domain.employee.mapper.EmployeeProjectDTOMapper;
import thesis.domain.task.mapper.TaskFormDTOMapper;
import thesis.domain.task.model.TaskStatusDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

import static thesis.domain.paging.PagingHelper.getPaging;
import static thesis.domain.paging.PagingHelper.getSorting;


@AllArgsConstructor
@Service

public class EmployeeService {

    private final AccountRepository accountRepository;
    private final AccountDetailsRepository accountDetailsRepository;
    private final ProjectAccountRepository projectAccountRepository;
    private final ProjectRepository projectRepository;

    private final TaskRepository taskRepository;
    private final TaskFormRepository taskFormRepository;
    private final EmployeeDTOMapper employeeDTOMapper;
    private final EmployeeProjectDTOMapper employeeProjectDTOMapper;
    private final TaskFormDTOMapper taskFormDTOMapper;
    private final EmployeeTasksDTOMapper employeeTasksDTOMapper;
    private final EmployeeTaskDTOMapper employeeTaskDTOMapper;

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public EmployeeDTO getEmployee(UUID id) {
        var account = accountRepository.findById(id).orElseThrow();
        var details = accountDetailsRepository.findByAccount(account).orElseThrow();
        return employeeDTOMapper.map(details);
    }

    public EmployeeProjectsDTO getEmployeeProjects(UUID id, PagingSettings pagingSettings) {
        if (!accountRepository.existsById(id)) {
            throw new UsernameNotFoundException("employee not found"); // TODO: 17/12/2022  exception handling
        }

        var account = accountRepository
                .findById(id)
                .orElseThrow();

        var projects = projectAccountRepository.findAllByAccount_Id(account.getId(), pagingSettings.getPageable())
                .orElseThrow()
                .map(ProjectAccount::getProject);

        var paging = getPaging(pagingSettings, projects);
        var sorting = getSorting(pagingSettings);

        return EmployeeProjectsDTO.builder()
                .employeeProjectDTOList(projects.map(employeeProjectDTOMapper::map).toList())
                .paging(paging)
                .sorting(sorting)
                .build();
    }

    public EmployeeProjectsToApproveDTO getEmployeeProjectsToApprove(UUID accountId, Date startDate, Date endDate) {
        var account = accountRepository
                .findById(accountId)
                .orElseThrow();

        var tasks = taskRepository
                .findByAccountIdAndStatusAndDateFromStartingWithAAndDateToEndingWith(
                        account.getId(),
                        TaskStatus.PENDING,
                        startDate,
                        endDate
                )
                .orElseThrow();

        var employeeProjects = tasks.stream()
                .collect(Collectors.groupingBy(task -> task.getForm().getProject().getId()))
                .entrySet().stream()
                .map(uuidListEntry -> {
                    var project = projectRepository.findById(uuidListEntry.getKey()).orElseThrow();

                    return EmployeeProjectToApproveDTO.builder()
                            .project(employeeProjectDTOMapper.map(project))
                            .count(uuidListEntry.getValue().size())
                            .build();
                })
                .collect(Collectors.toList());

        return EmployeeProjectsToApproveDTO.builder()
                .projects(employeeProjects)
                .build();

    }

    @Transactional
    public void sendProjectsToApprove(UUID accountId, List<UUID> projectIds, Date startDate, Date endDate) {
        var tasks = taskRepository
                .findByAccountIdAndStatusAndDateFromStartingWithAAndDateToEndingWith(
                        accountId,
                        TaskStatus.PENDING,
                        startDate,
                        endDate
                )
                .orElseThrow();

        projectIds.forEach(projectId -> {
            tasks.stream()
                    .filter(task -> task.getForm().getProject().getId().compareTo(projectId) == 0)
                    .forEach(task -> task.setStatus(TaskStatus.APPROVED));

            taskRepository.saveAll(tasks);

        });

    }

    public EmployeeTasksDTO getEmployeeTasks(UUID employeeId, Date startDate, Date endDate) {
        var tasks = taskRepository.findByAccountIdAndDateFromStartingWithAndDateToEndingWith(employeeId, startDate, endDate).orElseThrow();

        return EmployeeTasksDTO.builder().tasks(employeeTasksDTOMapper.map(tasks)).build();
    }

    public EmployeeTaskDTO getEmployeeTask(UUID employeeId, UUID taskId) {
        var task = taskRepository.findById(taskId).orElseThrow();

        return employeeTaskDTOMapper.map(task);
    }

    @Transactional
    public UUID createEmployeeTask(UUID employeeId, EmployeeTaskCreatePayloadDTO payloadDTO) {
        var account = accountRepository.findById(employeeId).orElseThrow();
        var taskForm = taskFormRepository.findById(payloadDTO.taskId()).orElseThrow();

        var task = Task.builder()
                .account(account)
                .form(taskForm)
                .createdAt(new Date())
                .dateFrom(payloadDTO.startDate())
                .dateTo(payloadDTO.endDate())
                .status(TaskStatus.LOGGED)
                .build();

        taskRepository.save(task);

        return task.getId();
    }

    @Transactional
    public UUID updateEmployeeTask(UUID employeeId, EmployeeTaskUpdatePayloadDTO payloadDTO) {
        var account = accountRepository.findById(employeeId).orElseThrow();
        var project = projectRepository.findById(payloadDTO.projectId()).orElseThrow();
        var taskForm = taskFormRepository.findById(payloadDTO.taskId()).orElseThrow();

        var task = taskRepository.findById(payloadDTO.id()).orElseThrow();

        task.setDateFrom(payloadDTO.startDate());
        task.setDateTo(payloadDTO.endDate());
        task.setForm(taskForm);

        taskRepository.save(task);

        return task.getId();
    }

    public CalendarDTO getCalendar(UUID employeeId, Date date) {
        var account = accountRepository.findById(employeeId).orElseThrow();
        var listOfDate = getFirstAndLastDayOfMonth(date);
        var tasks = taskRepository.findByAccountIdAndDateFromStartingWithAndDateToEndingWith(account.getId(), listOfDate.get(0), listOfDate.get(1)).orElseThrow();

        return new CalendarDTO(getCalendarTaskDTOList(tasks));
    }

    public List<CalendarTaskDTO> getCalendarTaskDTOList(List<Task> tasks) {
        var groupedTasks = tasks.stream()
                .collect(
                        Collectors.groupingBy(
                                task -> getSimplyDate(task.getDateFrom()),
                                Collectors.groupingBy(
                                        Task::getStatus,
                                        Collectors.counting()
                                )
                        )
                );

        return groupedTasks
                .entrySet()
                .stream()
                .map(
                        dateMapEntry -> new CalendarTaskDTO(
                                dateMapEntry.getKey(),
                                getStatus(dateMapEntry.getValue())
                        )
                ).toList();
    }

    private TaskStatusDTO getStatus(Map<TaskStatus, Long> tasks) {

        if (tasks.get(TaskStatus.PENDING) != null && tasks.get(TaskStatus.PENDING)  > 0) {
            return TaskStatusDTO.PENDING;
        } else if (tasks.get(TaskStatus.REJECTED) != null && tasks.get(TaskStatus.REJECTED) > 0) {
            return TaskStatusDTO.REJECTED;
        } else if (tasks.get(TaskStatus.LOGGED) != null && tasks.get(TaskStatus.LOGGED) > 0) {
            return TaskStatusDTO.LOGGED;
        } else if (tasks.get(TaskStatus.APPROVED) != null && tasks.get(TaskStatus.APPROVED) > 0) {
            return TaskStatusDTO.APPROVED;
        }
        return TaskStatusDTO.NONE;
    }

    public List<Date> getFirstAndLastDayOfMonth(Date date) {
        LocalDate currentDate = Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        YearMonth yearMonth = YearMonth.of(currentDate.getYear(), currentDate.getMonth());

        var firstDay = Date
                .from(yearMonth
                        .atDay(1)
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
                );
        var lastDay = Date
                .from(yearMonth
                        .atEndOfMonth()
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
                );

        return List.of(
                firstDay,
                lastDay
        );

    }

    private Date getSimplyDate(Date date) {
        try {
            var result = format.parse(format.format(date));
            return result;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}