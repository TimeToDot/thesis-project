package thesis.domain.employee.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import thesis.data.project.model.Project;
import thesis.domain.employee.model.EmployeeProjectDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-15T17:16:22+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Private Build)"
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
