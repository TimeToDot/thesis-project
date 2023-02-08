package pl.thesis.domain.project.model.approval;

import lombok.Builder;
import pl.thesis.domain.employee.model.CalendarTaskDTO;

import java.util.List;
import java.util.UUID;

@Builder
public record ProjectCalendarDTO(

        UUID employeeId,
        UUID projectEmployeeId,
        List<CalendarTaskDTO> tasks
) {
}
