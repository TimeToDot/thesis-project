package pl.thesis.domain.employee;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import pl.thesis.data.account.AccountRepository;
import pl.thesis.data.account.model.Account;
import pl.thesis.data.project.ProjectRepository;
import pl.thesis.data.project.model.Project;
import pl.thesis.data.task.TaskRepository;
import pl.thesis.data.task.model.Task;
import pl.thesis.data.task.model.TaskStatus;
import pl.thesis.domain.employee.model.CalendarDTO;
import pl.thesis.domain.employee.model.CalendarTaskDTO;
import pl.thesis.domain.paging.PagingSettings;
import pl.thesis.domain.task.model.TaskStatusDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class EmployeeCalendarServiceDefault implements EmployeeCalendarService, CalendarProjectService {
    private final AccountRepository accountRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public CalendarDTO getCalendar(Long employeeId, Date date) {
        var account = accountRepository.findById(employeeId).orElseThrow();
        var daysOfMonth = getFirstAndLastDayOfMonth(date);
        var taskList = getTaskListForCalendar(account, daysOfMonth);

        return new CalendarDTO(getCalendarTaskDTOList(taskList));
    }

    @Override
    public CalendarDTO getEmployeeCalendarForProject(Long employeeId, Long projectId, Date date) {
        var account = accountRepository.findById(employeeId).orElseThrow();
        var project = projectRepository.findById(projectId).orElseThrow();
        var daysOfMonth = getFirstAndLastDayOfMonth(date);
        var taskList = getTaskListForEmployeeCalendar(account, project, daysOfMonth);


        return new CalendarDTO(getCalendarTaskDTOList(taskList));
    }


    @Override
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
                )
                .toList();
    }

    private DaysOfMonth getFirstAndLastDayOfMonth(Date date) {
        ZonedDateTime currentDate = Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault());

        ZonedDateTime firstDay = currentDate.withDayOfMonth(1).with(LocalTime.MIN);
        ZonedDateTime lastDay = currentDate.withDayOfMonth(currentDate.toLocalDate().lengthOfMonth()).with(LocalTime.MAX);

        Date firstDate = Date.from(firstDay.toInstant());
        Date lastDate = Date.from(lastDay.toInstant());

        return new DaysOfMonth(firstDate, lastDate);

    }

    private Date getSimplyDate(Date date) {
        try {
            return DateUtils.truncate(format.parse(format.format(date)), Calendar.DATE);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private TaskStatusDTO getStatus(Map<TaskStatus, Long> tasks) {

        if (tasks.get(TaskStatus.PENDING) != null && tasks.get(TaskStatus.PENDING) > 0) {
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

    private ArrayList<Task> getTaskListForCalendar(Account account, DaysOfMonth daysOfMonth) {
        var size = 100;
        var page = 1;
        var pageable = PagingSettings.builder().page(page).size(size).build().getPageable();
        var tasks = taskRepository.findByAccountIdAndDateFromBetween(account.getId(), daysOfMonth.firstDay, daysOfMonth.lastDay, pageable).orElseThrow();
        var taskList = new ArrayList<>(tasks.stream().toList());

        while (tasks.getTotalPages() > page) {
            page += 1;
            pageable = PagingSettings.builder().page(page).size(size).build().getPageable();
            tasks = taskRepository.findByAccountIdAndDateFromBetween(account.getId(), daysOfMonth.firstDay, daysOfMonth.lastDay, pageable).orElseThrow();
            taskList.addAll(tasks.stream().toList());
        }
        return taskList;
    }

    private ArrayList<Task> getTaskListForEmployeeCalendar(Account account, Project project, DaysOfMonth daysOfMonth) {
        var size = 100;
        var page = 1;
        var settings = PagingSettings.builder().page(page).size(size).build();
        var tasks = taskRepository.findByAccountIdAndFormProjectIdAndDateFromBetween(account.getId(), project.getId(), daysOfMonth.firstDay, daysOfMonth.lastDay, settings.getPageable()).orElseThrow();
        var taskList = new ArrayList<>(tasks.stream().toList());

        while (tasks.getTotalPages() > page) {
            page += 1;
            settings = PagingSettings.builder().page(page).size(size).build();
            tasks = taskRepository.findByAccountIdAndFormProjectIdAndDateFromBetween(account.getId(), project.getId(), daysOfMonth.firstDay, daysOfMonth.lastDay, settings.getPageable()).orElseThrow();
            taskList.addAll(tasks.stream().toList());
        }
        return taskList;
    }

    private record DaysOfMonth(
            Date firstDay,
            Date lastDay
    ){}
}
