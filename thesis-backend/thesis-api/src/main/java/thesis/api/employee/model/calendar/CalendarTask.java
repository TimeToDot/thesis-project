package thesis.api.employee.model.calendar;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record CalendarTask(

        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Warsaw")
        Date date,
        CalendarTaskStatus status
) {
}
