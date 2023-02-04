package thesis.domain.employee.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import thesis.data.project.model.Project;
import thesis.domain.employee.model.EmployeeProjectDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-04T17:16:35+0100",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.33.0.v20221215-1352, environment: Java 17.0.5 (Eclipse Adoptium)"
)
@Component
public class EmployeeProjectDTOMapperImpl implements EmployeeProjectDTOMapper {

    @Override
    public EmployeeProjectDTO map(Project project) {
        if ( project == null ) {
            return null;
        }

        EmployeeProjectDTO.EmployeeProjectDTOBuilder employeeProjectDTO = EmployeeProjectDTO.builder();

        employeeProjectDTO.id( project.getId() );
        employeeProjectDTO.name( project.getName() );

        return employeeProjectDTO.build();
    }
}
