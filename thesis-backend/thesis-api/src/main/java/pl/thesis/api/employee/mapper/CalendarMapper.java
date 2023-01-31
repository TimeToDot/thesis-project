package pl.thesis.api.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.thesis.api.employee.model.calendar.EmployeeCalendarResponse;
import pl.thesis.domain.employee.model.CalendarDTO;
import pl.thesis.domain.mapper.MapStructConfig;

@Mapper(
        config = MapStructConfig.class,
        uses = CalendarTaskMapper.class
)
public interface CalendarMapper {

    @Mapping(target = "tasks", source = "tasks")
    EmployeeCalendarResponse map (CalendarDTO calendarDTO);
}
