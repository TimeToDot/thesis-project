package thesis.api.employee.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thesis.api.employee.model.task.EmployeeTaskResponse;
import thesis.api.employee.model.task.EmployeeTasksResponse;
import thesis.domain.employee.model.EmployeeTaskDTO;
import thesis.domain.employee.model.EmployeeTasksDTO;
import thesis.domain.paging.Paging;
import thesis.domain.paging.Sorting;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-15T17:16:25+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Private Build)"
)
@Component
public class EmployeeTasksMapperImpl implements EmployeeTasksMapper {

    @Autowired
    private EmployeeTaskMapper employeeTaskMapper;

    @Override
    public EmployeeTasksResponse map(EmployeeTasksDTO employeeTasksDTO) {
        if ( employeeTasksDTO == null ) {
            return null;
        }

        List<EmployeeTaskResponse> tasks = null;
        Paging paging = null;
        Sorting sorting = null;

        tasks = employeeTaskDTOListToEmployeeTaskResponseList( employeeTasksDTO.tasks() );
        paging = employeeTasksDTO.paging();
        sorting = employeeTasksDTO.sorting();

        EmployeeTasksResponse employeeTasksResponse = new EmployeeTasksResponse( tasks, paging, sorting );

        return employeeTasksResponse;
    }

    protected List<EmployeeTaskResponse> employeeTaskDTOListToEmployeeTaskResponseList(List<EmployeeTaskDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<EmployeeTaskResponse> list1 = new ArrayList<EmployeeTaskResponse>( list.size() );
        for ( EmployeeTaskDTO employeeTaskDTO : list ) {
            list1.add( employeeTaskMapper.map( employeeTaskDTO ) );
        }

        return list1;
    }
}
