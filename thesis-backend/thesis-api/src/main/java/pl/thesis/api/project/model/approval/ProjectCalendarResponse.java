package pl.thesis.api.project.model.approval;

import lombok.Builder;
import pl.thesis.domain.employee.model.CalendarTaskDTO;

import java.util.List;
import java.util.UUID;

@Builder
public record ProjectCalendarResponse(

        String employeeId,
        String projectEmployeeId,
        List<CalendarTaskDTO> tasks
) {
}
