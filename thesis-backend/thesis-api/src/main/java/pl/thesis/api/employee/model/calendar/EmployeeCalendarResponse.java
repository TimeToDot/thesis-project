package pl.thesis.api.employee.model.calendar;

import java.util.List;

public record EmployeeCalendarResponse(
        List<CalendarTask> tasks
) {
}
