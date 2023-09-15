package pl.thesis.domain.employee;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.thesis.data.account.AccountRepository;
import pl.thesis.data.project.AccountProjectRepository;
import pl.thesis.data.project.ProjectRepository;
import pl.thesis.data.task.TaskRepository;
import pl.thesis.data.task.model.Task;
import pl.thesis.data.task.model.TaskStatus;
import pl.thesis.domain.employee.mapper.EmployeeProjectDTOMapper;
import pl.thesis.domain.employee.mapper.EmployeeProjectDetailsDTOMapper;
import pl.thesis.domain.employee.model.EmployeeProjectToApproveDTO;
import pl.thesis.domain.employee.model.EmployeeProjectsApproveTemp;
import pl.thesis.domain.employee.model.EmployeeProjectsDTO;
import pl.thesis.domain.employee.model.EmployeeProjectsToApproveDTO;
import pl.thesis.domain.paging.PagingHelper;
import pl.thesis.domain.paging.PagingSettings;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class EmployeeProjectsService {

    private final AccountRepository accountRepository;
    private final AccountProjectRepository accountProjectRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final EmployeeProjectDTOMapper employeeProjectDTOMapper;
    private final EmployeeProjectDetailsDTOMapper employeeProjectDetailsDTOMapper;

    public EmployeeProjectsDTO getEmployeeProjectToApproveList(Long id, PagingSettings pagingSettings) {
        if (!accountRepository.existsById(id)) {
            throw new UsernameNotFoundException("employee not found");
        }

        var account = accountRepository.findById(id).orElseThrow();
        var accountProjects = accountProjectRepository.findAllByAccountId(account.getId(), pagingSettings.getPageable()).orElseThrow();

        var paging = PagingHelper.getPaging(pagingSettings, accountProjects);
        var sorting = PagingHelper.getSorting(pagingSettings);

        return EmployeeProjectsDTO.builder()
                .employeeProjectDTOList(accountProjects.map(employeeProjectDetailsDTOMapper::map).toList())
                .paging(paging)
                .sorting(sorting)
                .build();
    }

    public EmployeeProjectsToApproveDTO getEmployeeProjectsToApprove(Long accountId, Date startDate, Date endDate) {
        var account = accountRepository
                .findById(accountId)
                .orElseThrow();

        var tasks = taskRepository
                .findByAccountIdAndStatusAndDateFromBetween(
                        account.getId(),
                        TaskStatus.LOGGED,
                        startDate,
                        endDate
                )
                .orElseThrow();

        var employeeProjects = getEmployeeProjectToApproveList(tasks);

        return EmployeeProjectsToApproveDTO.builder()
                .projects(employeeProjects)
                .build();

    }

    @Transactional
    public void sendProjectsToApprove(EmployeeProjectsApproveTemp dto, Date startDate, Date endDate) {
        var tasks = taskRepository
                .findByAccountIdAndStatusAndDateFromBetween(
                        dto.id(),
                        TaskStatus.LOGGED,
                        startDate,
                        endDate
                )
                .orElseThrow();

        dto.projects().forEach(projectId -> {
            tasks.stream()
                    .filter(task -> task.getForm().getProject().getId().compareTo(projectId) == 0)
                    .forEach(task -> task.setStatus(TaskStatus.PENDING));

            taskRepository.saveAll(tasks);

        });

    }

    private List<EmployeeProjectToApproveDTO> getEmployeeProjectToApproveList(List<Task> tasks) {
        return tasks.stream()
                .collect(Collectors.groupingBy(task -> task.getForm().getProject().getId()))
                .entrySet().stream()
                .map(uuidListEntry -> {
                    var project = projectRepository.findById(uuidListEntry.getKey()).orElseThrow();

                    return EmployeeProjectToApproveDTO.builder()
                            .project(employeeProjectDTOMapper.map(project))
                            .count(uuidListEntry.getValue().size())
                            .build();
                })
                .toList();
    }
}
