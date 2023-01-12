package thesis.domain.employee;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import thesis.data.account.AccountDetailsRepository;
import thesis.data.account.AccountRepository;
import thesis.data.project.ProjectAccountRepository;
import thesis.data.project.ProjectRepository;
import thesis.data.project.model.ProjectAccount;
import thesis.data.task.TaskRepository;
import thesis.data.task.model.TaskStatus;
import thesis.domain.employee.mapper.EmployeeTaskDTOMapper;
import thesis.domain.employee.mapper.EmployeeTasksDTOMapper;
import thesis.domain.employee.model.*;
import thesis.domain.paging.PagingSettings;
import thesis.domain.employee.mapper.EmployeeDTOMapper;
import thesis.domain.employee.mapper.EmployeeProjectDTOMapper;
import thesis.domain.task.mapper.TaskFormDTOMapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
    private final EmployeeDTOMapper employeeDTOMapper;
    private final EmployeeProjectDTOMapper employeeProjectDTOMapper;
    private final TaskFormDTOMapper taskFormDTOMapper;
    private final EmployeeTasksDTOMapper employeeTasksDTOMapper;

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

        return employeeTasksDTOMapper.map(tasks);
    }


    private LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}