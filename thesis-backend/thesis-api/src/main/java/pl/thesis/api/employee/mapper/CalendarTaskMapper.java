package pl.thesis.api.employee.mapper;

import org.mapstruct.Mapper;
import pl.thesis.api.employee.model.calendar.CalendarTask;
import pl.thesis.domain.employee.model.CalendarTaskDTO;
import pl.thesis.domain.mapper.MapStructConfig;

@Mapper(config = MapStructConfig.class)
public interface CalendarTaskMapper {

    CalendarTask map(CalendarTaskDTO task);
}
