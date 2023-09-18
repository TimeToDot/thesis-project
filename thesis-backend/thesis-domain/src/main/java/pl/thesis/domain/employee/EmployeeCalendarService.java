package pl.thesis.domain.employee;

import pl.thesis.domain.employee.model.CalendarDTO;

import java.util.Date;

public interface EmployeeCalendarService {
    CalendarDTO getCalendar(Long employeeId, Date date);
}
