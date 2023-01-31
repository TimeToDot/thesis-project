package pl.thesis.api.employee.model.task.temp;

import org.springframework.format.annotation.DateTimeFormat;
import pl.thesis.api.employee.model.project.temp.ProjectTempResponse;
import pl.thesis.domain.task.model.TaskStatusDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record EmployeeTaskTemp(
        String id,
        String employeeId,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate startDate,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate endDate,
        @DateTimeFormat(pattern = "hh:MM")
        LocalTime startTime,
        @DateTimeFormat(pattern = "hh:MM")
        LocalTime endTime,
        ProjectTempResponse project,
        TaskTemp task,
        TaskStatusDTO status
) {
}
