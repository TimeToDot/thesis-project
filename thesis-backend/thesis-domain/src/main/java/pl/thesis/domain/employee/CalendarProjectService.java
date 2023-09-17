package pl.thesis.domain.employee;

import pl.thesis.data.task.model.Task;
import pl.thesis.domain.employee.model.CalendarDTO;
import pl.thesis.domain.employee.model.CalendarTaskDTO;

import java.util.Date;
import java.util.List;

public interface CalendarProjectService {
    CalendarDTO getEmployeeCalendarForProject(Long employeeId, Long projectId, Date date);

    List<CalendarTaskDTO> getCalendarTaskDTOList(List<Task> tasks);
}
