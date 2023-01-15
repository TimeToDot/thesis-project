package thesis.api.employee.mapper;

import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import thesis.api.employee.model.project.EmployeeProjectResponse;
import thesis.api.employee.model.project.EmployeeProjectToApproveResponse;
import thesis.domain.employee.model.EmployeeProjectDTO;
import thesis.domain.employee.model.EmployeeProjectToApproveDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-15T17:16:25+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Private Build)"
)
@Component
public class EmployeeProjectMapperImpl implements EmployeeProjectMapper {

    @Override
    public EmployeeProjectResponse map(EmployeeProjectDTO employeeDTO) {
        if ( employeeDTO == null ) {
            return null;
        }

        UUID id = null;
        String name = null;
        String imagePath = null;

        id = employeeDTO.id();
        name = employeeDTO.name();
        imagePath = employeeDTO.imagePath();

        EmployeeProjectResponse employeeProjectResponse = new EmployeeProjectResponse( id, name, imagePath );

        return employeeProjectResponse;
    }

    @Override
    public EmployeeProjectToApproveResponse toApproveMap(EmployeeProjectToApproveDTO projectToApproveDTO) {
        if ( projectToApproveDTO == null ) {
            return null;
        }

        EmployeeProjectResponse project = null;
        Integer count = null;

        project = map( projectToApproveDTO.project() );
        count = projectToApproveDTO.count();

        EmployeeProjectToApproveResponse employeeProjectToApproveResponse = new EmployeeProjectToApproveResponse( project, count );

        return employeeProjectToApproveResponse;
    }
}
