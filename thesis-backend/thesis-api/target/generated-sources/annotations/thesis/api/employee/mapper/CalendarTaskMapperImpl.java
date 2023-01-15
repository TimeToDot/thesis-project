package thesis.api.employee.mapper;

import java.util.Date;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import thesis.api.employee.model.calendar.CalendarTask;
import thesis.api.employee.model.calendar.CalendarTaskStatus;
import thesis.domain.employee.model.CalendarTaskDTO;
import thesis.domain.task.model.TaskStatusDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-15T17:16:26+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Private Build)"
)
@Component
public class CalendarTaskMapperImpl implements CalendarTaskMapper {

    @Override
    public CalendarTask map(CalendarTaskDTO task) {
        if ( task == null ) {
            return null;
        }

        Date date = null;
        CalendarTaskStatus status = null;

        date = task.date();
        status = taskStatusDTOToCalendarTaskStatus( task.status() );

        CalendarTask calendarTask = new CalendarTask( date, status );

        return calendarTask;
    }

    protected CalendarTaskStatus taskStatusDTOToCalendarTaskStatus(TaskStatusDTO taskStatusDTO) {
        if ( taskStatusDTO == null ) {
            return null;
        }

        CalendarTaskStatus calendarTaskStatus;

        switch ( taskStatusDTO ) {
            case PENDING: calendarTaskStatus = CalendarTaskStatus.PENDING;
            break;
            case REJECTED: calendarTaskStatus = CalendarTaskStatus.REJECTED;
            break;
            case LOGGED: calendarTaskStatus = CalendarTaskStatus.LOGGED;
            break;
            case APPROVED: calendarTaskStatus = CalendarTaskStatus.APPROVED;
            break;
            case NONE: calendarTaskStatus = CalendarTaskStatus.NONE;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + taskStatusDTO );
        }

        return calendarTaskStatus;
    }
}
