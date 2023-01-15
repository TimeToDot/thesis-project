package thesis.api.employee.mapper;

import java.util.Date;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import thesis.api.employee.model.task.EmployeeTaskCreatePayload;
import thesis.domain.employee.model.EmployeeTaskCreatePayloadDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-15T17:16:25+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Private Build)"
)
@Component
public class EmployeeTaskCreatePayloadMapperImpl implements EmployeeTaskCreatePayloadMapper {

    @Override
    public EmployeeTaskCreatePayloadDTO map(EmployeeTaskCreatePayload task) {
        if ( task == null ) {
            return null;
        }

        Date startDate = null;
        Date endDate = null;
        UUID projectId = null;
        UUID taskId = null;

        startDate = task.startDate();
        endDate = task.endDate();
        projectId = task.projectId();
        taskId = task.taskId();

        EmployeeTaskCreatePayloadDTO employeeTaskCreatePayloadDTO = new EmployeeTaskCreatePayloadDTO( startDate, endDate, projectId, taskId );

        return employeeTaskCreatePayloadDTO;
    }
}
