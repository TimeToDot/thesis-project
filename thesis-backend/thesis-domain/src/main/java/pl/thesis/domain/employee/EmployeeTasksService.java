package pl.thesis.domain.employee;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.thesis.data.account.AccountRepository;
import pl.thesis.data.project.ProjectRepository;
import pl.thesis.data.project.model.AccountProject;
import pl.thesis.data.task.TaskFormRepository;
import pl.thesis.data.task.TaskRepository;
import pl.thesis.data.task.model.Task;
import pl.thesis.data.task.model.TaskStatus;
import pl.thesis.domain.employee.mapper.EmployeeTaskDTOMapper;
import pl.thesis.domain.employee.mapper.EmployeeTasksDTOMapper;
import pl.thesis.domain.employee.model.EmployeeTaskCreatePayloadDTO;
import pl.thesis.domain.employee.model.EmployeeTaskDTO;
import pl.thesis.domain.employee.model.EmployeeTaskUpdatePayloadDTO;
import pl.thesis.domain.employee.model.EmployeeTasksDTO;
import pl.thesis.domain.paging.PagingHelper;
import pl.thesis.domain.paging.PagingSettings;

import java.util.Date;

@AllArgsConstructor
@Service
public class EmployeeTasksService {

    private final AccountRepository accountRepository;
    private final ProjectRepository projectRepository;

    private final TaskRepository taskRepository;
    private final TaskFormRepository taskFormRepository;
    private final EmployeeTasksDTOMapper employeeTasksDTOMapper;
    private final EmployeeTaskDTOMapper employeeTaskDTOMapper;

    public EmployeeTasksDTO getEmployeeTasks(Long employeeId, Date startDate, Date endDate, PagingSettings settings) {
        if (settings == null) {
            settings = new PagingSettings();
        }
        var tasks = taskRepository.findByAccountIdAndDateFromBetween(employeeId, startDate, endDate, settings.getPageable()).orElseThrow();

        var paging = PagingHelper.getPaging(settings, tasks);
        var sorting = PagingHelper.getSorting(settings);

        return EmployeeTasksDTO.builder()
                .tasks(employeeTasksDTOMapper.map(tasks.stream().toList()))
                .paging(paging)
                .sorting(sorting)
                .build();
    }

    public EmployeeTaskDTO getTask(Long employeeId, Long taskId) {
        var task = taskRepository.findById(taskId).orElseThrow();

        if (task.getAccount().getId().compareTo(employeeId) != 0){
            throw new RuntimeException("Task doesnt belong to employee with this positionId");
        }

        return employeeTaskDTOMapper.map(task);
    }

    @Transactional
    public Long createTask(Long employeeId, EmployeeTaskCreatePayloadDTO payloadDTO) {
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
    public Long updateTask(EmployeeTaskUpdatePayloadDTO payloadDTO) {
        var account = accountRepository.findById(payloadDTO.employeeId()).orElseThrow();
        var project = projectRepository.findById(payloadDTO.projectId()).orElseThrow();

        if(
                project
                        .getAccountProjects()
                        .stream()
                        .map(AccountProject::getAccount)
                        .noneMatch(account1 -> account1.getId().compareTo(account.getId()) == 0)
        ) {
            throw new RuntimeException("employee doesnt exist in this project");
        }

        var taskForm = taskFormRepository.findById(payloadDTO.taskId()).orElseThrow();
        var task = taskRepository.findById(payloadDTO.id()).orElseThrow();

        task.setDateFrom(payloadDTO.startDate());
        task.setDateTo(payloadDTO.endDate());
        task.setForm(taskForm);
        task.setStatus(TaskStatus.valueOf(payloadDTO.status().name()));

        taskRepository.save(task);

        return task.getId();
    }

    @Transactional
    public void deleteTask(Long employeeId, Long taskId) {
        var account = accountRepository.findById(employeeId).orElseThrow();
        var task = taskRepository.findById(taskId).orElseThrow();

        if (!account.getTasks().contains(task)){
            throw new RuntimeException("this task doesn't belong to user");
        }
        if (!(task.getStatus().compareTo(TaskStatus.LOGGED) == 0 || task.getStatus().compareTo(TaskStatus.REJECTED) == 0)){
            throw new RuntimeException("this task cannot be deleted!");
        }

        account.getTasks().removeIf(t -> t.getId().compareTo(task.getId()) == 0);

        accountRepository.save(account);

        taskRepository.deleteById(task.getId());
    }

}
