package thesis.domain.employee.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thesis.data.task.model.Task;
import thesis.domain.employee.model.EmployeeTaskDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-15T17:16:22+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Private Build)"
)
@Component
public class EmployeeTasksDTOMapperImpl implements EmployeeTasksDTOMapper {

    @Autowired
    private EmployeeTaskDTOMapper employeeTaskDTOMapper;

    @Override
    public List<EmployeeTaskDTO> map(List<Task> task) {
        if ( task == null ) {
            return null;
        }

        List<EmployeeTaskDTO> list = new ArrayList<EmployeeTaskDTO>( task.size() );
        for ( Task task1 : task ) {
            list.add( employeeTaskDTOMapper.map( task1 ) );
        }

        return list;
    }
}
