package pl.thesis.domain.employee.model;

import pl.thesis.domain.task.model.TaskStatusDTO;

import java.util.Date;

public record CalendarTaskDTO(
        Date date,
        TaskStatusDTO status
) {
}
