package pl.thesis.api.project.model.approval;

import lombok.Builder;
import pl.thesis.domain.employee.model.CalendarTaskDTO;

import java.util.List;

@Builder
public record ProjectCalendarResponse(

        String employeeId,
        String projectEmployeeId,
        List<CalendarTaskDTO> tasks
) {
}
