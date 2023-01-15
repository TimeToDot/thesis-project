package thesis.api.employee.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thesis.api.employee.model.EmployeeResponse;
import thesis.api.employee.model.EmployeesResponse;
import thesis.domain.employee.model.EmployeeDTO;
import thesis.domain.employee.model.EmployeesDTO;
import thesis.domain.paging.Paging;
import thesis.domain.paging.Sorting;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-15T17:16:26+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Private Build)"
)
@Component
public class EmployeesMapperImpl implements EmployeesMapper {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public EmployeesResponse map(EmployeesDTO employeesDTO) {
        if ( employeesDTO == null ) {
            return null;
        }

        List<EmployeeResponse> employees = null;
        Paging paging = null;
        Sorting sorting = null;

        employees = employeeDTOListToEmployeeResponseList( employeesDTO.employees() );
        paging = employeesDTO.paging();
        sorting = employeesDTO.sorting();

        EmployeesResponse employeesResponse = new EmployeesResponse( employees, paging, sorting );

        return employeesResponse;
    }

    protected List<EmployeeResponse> employeeDTOListToEmployeeResponseList(List<EmployeeDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<EmployeeResponse> list1 = new ArrayList<EmployeeResponse>( list.size() );
        for ( EmployeeDTO employeeDTO : list ) {
            list1.add( employeeMapper.map( employeeDTO ) );
        }

        return list1;
    }
}
