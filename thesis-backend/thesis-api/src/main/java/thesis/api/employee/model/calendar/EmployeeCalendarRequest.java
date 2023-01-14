package thesis.api.employee.model.calendar;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record EmployeeCalendarRequest(
        @JsonFormat(pattern = "MM-yyyy")
        Date date
) {
}
