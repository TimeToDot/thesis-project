package thesis.api.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import thesis.api.employee.model.calendar.EmployeeCalendarResponse;
import thesis.domain.employee.model.CalendarDTO;
import thesis.domain.mapper.MapStructConfig;

@Mapper(
        config = MapStructConfig.class,
        uses = CalendarTaskMapper.class
)
public interface CalendarMapper {

    @Mapping(target = "tasks", source = "tasks")
    EmployeeCalendarResponse map (CalendarDTO calendarDTO);
}
