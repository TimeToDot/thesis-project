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
    date = "2023-02-04T17:16:43+0100",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.33.0.v20221215-1352, environment: Java 17.0.5 (Eclipse Adoptium)"
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

        EmployeeProjectToApproveResponse employeeProjectToApproveResponse = new EmployeeProjectToApproveResponse( project, count );

        return employeeProjectToApproveResponse;
    }
}
