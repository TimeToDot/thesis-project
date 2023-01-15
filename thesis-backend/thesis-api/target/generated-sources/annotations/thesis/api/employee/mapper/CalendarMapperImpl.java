package thesis.api.employee.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thesis.api.employee.model.calendar.CalendarTask;
import thesis.api.employee.model.calendar.EmployeeCalendarResponse;
import thesis.domain.employee.model.CalendarDTO;
import thesis.domain.employee.model.CalendarTaskDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-15T17:16:26+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Private Build)"
)
@Component
public class CalendarMapperImpl implements CalendarMapper {

    @Autowired
    private CalendarTaskMapper calendarTaskMapper;

    @Override
    public EmployeeCalendarResponse map(CalendarDTO calendarDTO) {
        if ( calendarDTO == null ) {
            return null;
        }

        List<CalendarTask> tasks = null;

        tasks = calendarTaskDTOListToCalendarTaskList( calendarDTO.tasks() );

        EmployeeCalendarResponse employeeCalendarResponse = new EmployeeCalendarResponse( tasks );

        return employeeCalendarResponse;
    }

    protected List<CalendarTask> calendarTaskDTOListToCalendarTaskList(List<CalendarTaskDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<CalendarTask> list1 = new ArrayList<CalendarTask>( list.size() );
        for ( CalendarTaskDTO calendarTaskDTO : list ) {
            list1.add( calendarTaskMapper.map( calendarTaskDTO ) );
        }

        return list1;
    }
}
