package thesis.api.employee.mapper;

import java.util.Date;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import thesis.api.employee.model.task.TaskResponse;
import thesis.domain.task.model.TaskFormDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-15T17:16:25+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Private Build)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskResponse map(TaskFormDto taskFormDto) {
        if ( taskFormDto == null ) {
            return null;
        }

        UUID id = null;
        String name = null;
        String description = null;
        Date creationDate = null;
        UUID projectId = null;

        id = taskFormDto.id();
        name = taskFormDto.name();
        description = taskFormDto.description();
        creationDate = taskFormDto.creationDate();
        projectId = taskFormDto.projectId();

        TaskResponse taskResponse = new TaskResponse( id, name, description, creationDate, projectId );

        return taskResponse;
    }
}
