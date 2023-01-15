package thesis.api.employee.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thesis.api.employee.model.project.EmployeeProjectResponse;
import thesis.api.employee.model.project.EmployeeProjectToApproveResponse;
import thesis.api.employee.model.project.EmployeeProjectsResponse;
import thesis.api.employee.model.project.EmployeeProjectsToApproveResponse;
import thesis.domain.employee.model.EmployeeProjectDTO;
import thesis.domain.employee.model.EmployeeProjectToApproveDTO;
import thesis.domain.employee.model.EmployeeProjectsDTO;
import thesis.domain.employee.model.EmployeeProjectsToApproveDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-15T17:16:26+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Private Build)"
)
@Component
public class EmployeeProjectsMapperImpl implements EmployeeProjectsMapper {

    @Autowired
    private EmployeeProjectMapper employeeProjectMapper;

    @Override
    public EmployeeProjectsResponse map(EmployeeProjectsDTO employeeProjectsDTO) {
        if ( employeeProjectsDTO == null ) {
            return null;
        }

        EmployeeProjectsResponse.EmployeeProjectsResponseBuilder employeeProjectsResponse = EmployeeProjectsResponse.builder();

        employeeProjectsResponse.projects( employeeProjectDTOListToEmployeeProjectResponseList( employeeProjectsDTO.employeeProjectDTOList() ) );
        employeeProjectsResponse.paging( employeeProjectsDTO.paging() );
        employeeProjectsResponse.sorting( employeeProjectsDTO.sorting() );

        return employeeProjectsResponse.build();
    }

    @Override
    public EmployeeProjectsToApproveResponse toApproveMap(EmployeeProjectsToApproveDTO employeeProjectsDTO) {
        if ( employeeProjectsDTO == null ) {
            return null;
        }

        List<EmployeeProjectToApproveResponse> projects = null;

        projects = employeeProjectToApproveDTOListToEmployeeProjectToApproveResponseList( employeeProjectsDTO.projects() );

        EmployeeProjectsToApproveResponse employeeProjectsToApproveResponse = new EmployeeProjectsToApproveResponse( projects );

        return employeeProjectsToApproveResponse;
    }

    protected List<EmployeeProjectResponse> employeeProjectDTOListToEmployeeProjectResponseList(List<EmployeeProjectDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<EmployeeProjectResponse> list1 = new ArrayList<EmployeeProjectResponse>( list.size() );
        for ( EmployeeProjectDTO employeeProjectDTO : list ) {
            list1.add( employeeProjectMapper.map( employeeProjectDTO ) );
        }

        return list1;
    }

    protected List<EmployeeProjectToApproveResponse> employeeProjectToApproveDTOListToEmployeeProjectToApproveResponseList(List<EmployeeProjectToApproveDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<EmployeeProjectToApproveResponse> list1 = new ArrayList<EmployeeProjectToApproveResponse>( list.size() );
        for ( EmployeeProjectToApproveDTO employeeProjectToApproveDTO : list ) {
            list1.add( employeeProjectMapper.toApproveMap( employeeProjectToApproveDTO ) );
        }

        return list1;
    }
}
