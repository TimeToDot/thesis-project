package thesis.domain.task.mapper;

import java.util.Date;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import thesis.data.project.model.Project;
import thesis.data.task.model.TaskForm;
import thesis.domain.task.model.TaskFormDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-04T17:16:35+0100",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.33.0.v20221215-1352, environment: Java 17.0.5 (Eclipse Adoptium)"
)
@Component
public class TaskFormDTOMapperImpl implements TaskFormDTOMapper {

    @Override
    public TaskFormDto map(TaskForm taskForm) {
        if ( taskForm == null ) {
            return null;
        }

        UUID id = null;
        String name = null;
        String description = null;
        Date creationDate = null;
        UUID projectId = null;

        id = taskForm.getId();
        name = taskForm.getName();
        description = taskForm.getDescription();
        creationDate = taskForm.getCreatedAt();
        projectId = taskFormProjectId( taskForm );

        TaskFormDto taskFormDto = new TaskFormDto( id, name, description, creationDate, projectId );

        return taskFormDto;
    }

    private UUID taskFormProjectId(TaskForm taskForm) {
        if ( taskForm == null ) {
            return null;
        }
        Project project = taskForm.getProject();
        if ( project == null ) {
            return null;
        }
        UUID id = project.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
