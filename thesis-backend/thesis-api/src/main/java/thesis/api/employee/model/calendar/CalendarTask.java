package thesis.api.employee.model.calendar;

import java.util.Date;

public record CalendarTask(
        Date date,
        CalendarTaskStatus status
) {
}
