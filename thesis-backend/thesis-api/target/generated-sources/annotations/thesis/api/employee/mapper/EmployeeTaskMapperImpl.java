package thesis.api.employee.mapper;

import java.util.Date;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thesis.api.employee.model.project.EmployeeProjectResponse;
import thesis.api.employee.model.task.EmployeeTaskResponse;
import thesis.api.employee.model.task.TaskResponse;
import thesis.data.task.model.TaskStatus;
import thesis.domain.employee.model.EmployeeTaskDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-15T17:16:25+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Private Build)"
)
@Component
public class EmployeeTaskMapperImpl implements EmployeeTaskMapper {

    @Autowired
    private EmployeeProjectMapper employeeProjectMapper;
    @Autowired
    private TaskMapper taskMapper;

    @Override
    public EmployeeTaskResponse map(EmployeeTaskDTO employeeDTO) {
        if ( employeeDTO == null ) {
            return null;
        }

        UUID id = null;
        Date startDate = null;
        Date endDate = null;
        EmployeeProjectResponse project = null;
        TaskResponse task = null;
        TaskStatus status = null;

        id = employeeDTO.id();
        startDate = employeeDTO.startDate();
        endDate = employeeDTO.endDate();
        project = employeeProjectMapper.map( employeeDTO.project() );
        task = taskMapper.map( employeeDTO.task() );
        status = employeeDTO.status();

        EmployeeTaskResponse employeeTaskResponse = new EmployeeTaskResponse( id, startDate, endDate, project, task, status );

        return employeeTaskResponse;
    }
}
