package pl.thesis.domain.project.model.approval;

import lombok.Builder;
import pl.thesis.domain.employee.model.CalendarTaskDTO;

import java.util.List;

@Builder
public record ProjectCalendarDTO(

        Long employeeId,
        Long projectEmployeeId,
        List<CalendarTaskDTO> tasks
) {
}
