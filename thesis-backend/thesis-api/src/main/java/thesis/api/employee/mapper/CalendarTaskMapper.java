package thesis.api.employee.mapper;

import org.mapstruct.Mapper;
import thesis.api.employee.model.calendar.CalendarTask;
import thesis.domain.employee.model.CalendarTaskDTO;
import thesis.domain.mapper.MapStructConfig;

@Mapper(config = MapStructConfig.class)
public interface CalendarTaskMapper {

    CalendarTask map(CalendarTaskDTO task);
}
