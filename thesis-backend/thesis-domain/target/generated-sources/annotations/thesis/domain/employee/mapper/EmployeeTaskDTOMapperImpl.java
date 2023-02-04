package thesis.domain.employee.mapper;

import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thesis.data.project.model.Project;
import thesis.data.task.model.Task;
import thesis.data.task.model.TaskForm;
import thesis.domain.employee.model.EmployeeTaskDTO;
import thesis.domain.task.mapper.TaskFormDTOMapper;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-04T17:16:36+0100",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.33.0.v20221215-1352, environment: Java 17.0.5 (Eclipse Adoptium)"
)
@Component
public class EmployeeTaskDTOMapperImpl implements EmployeeTaskDTOMapper {

    @Autowired
    private EmployeeProjectDTOMapper employeeProjectDTOMapper;
    @Autowired
    private TaskFormDTOMapper taskFormDTOMapper;

    @Override
    public EmployeeTaskDTO map(Task task) {
        if ( task == null ) {
            return null;
        }

        EmployeeTaskDTO.EmployeeTaskDTOBuilder employeeTaskDTO = EmployeeTaskDTO.builder();

        employeeTaskDTO.id( task.getId() );
        employeeTaskDTO.startDate( task.getDateFrom() );
        employeeTaskDTO.endDate( task.getDateTo() );
        employeeTaskDTO.project( employeeProjectDTOMapper.map( taskFormProject( task ) ) );
        employeeTaskDTO.task( taskFormDTOMapper.map( task.getForm() ) );
        employeeTaskDTO.status( task.getStatus() );

        employeeTaskDTO.startTime( convertToLocalDateTime(dateFrom) );
        employeeTaskDTO.endTime( convertToLocalDateTime(dateTo) );

        return employeeTaskDTO.build();
    }

    private Project taskFormProject(Task task) {
        if ( task == null ) {
            return null;
        }
        TaskForm form = task.getForm();
        if ( form == null ) {
            return null;
        }
        Project project = form.getProject();
        if ( project == null ) {
            return null;
        }
        return project;
    }
}
