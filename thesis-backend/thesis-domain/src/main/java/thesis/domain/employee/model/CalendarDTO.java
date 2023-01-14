package thesis.domain.employee.model;

import java.util.List;

public record CalendarDTO(
        List<CalendarTaskDTO> tasks
) {
}
