package thesis.api.employee.mapper;

import java.util.Date;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import thesis.api.employee.model.task.EmployeeTaskUpdatePayload;
import thesis.api.employee.model.task.TaskStatus;
import thesis.domain.employee.model.EmployeeTaskUpdatePayloadDTO;
import thesis.domain.task.model.TaskStatusDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-15T17:16:26+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Private Build)"
)
@Component
public class EmployeeTaskUpdatePayloadMapperImpl implements EmployeeTaskUpdatePayloadMapper {

    @Override
    public EmployeeTaskUpdatePayloadDTO map(EmployeeTaskUpdatePayload task) {
        if ( task == null ) {
            return null;
        }

        UUID id = null;
        Date startDate = null;
        Date endDate = null;
        UUID projectId = null;
        UUID taskId = null;
        TaskStatusDTO status = null;

        id = task.id();
        startDate = task.startDate();
        endDate = task.endDate();
        projectId = task.projectId();
        taskId = task.taskId();
        status = taskStatusToTaskStatusDTO( task.status() );

        EmployeeTaskUpdatePayloadDTO employeeTaskUpdatePayloadDTO = new EmployeeTaskUpdatePayloadDTO( id, startDate, endDate, projectId, taskId, status );

        return employeeTaskUpdatePayloadDTO;
    }

    protected TaskStatusDTO taskStatusToTaskStatusDTO(TaskStatus taskStatus) {
        if ( taskStatus == null ) {
            return null;
        }

        TaskStatusDTO taskStatusDTO;

        switch ( taskStatus ) {
            case PENDING: taskStatusDTO = TaskStatusDTO.PENDING;
            break;
            case REJECTED: taskStatusDTO = TaskStatusDTO.REJECTED;
            break;
            case LOGGED: taskStatusDTO = TaskStatusDTO.LOGGED;
            break;
            case APPROVED: taskStatusDTO = TaskStatusDTO.APPROVED;
            break;
            case NONE: taskStatusDTO = TaskStatusDTO.NONE;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + taskStatus );
        }

        return taskStatusDTO;
    }
}
