package thesis.domain.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import thesis.data.project.model.Project;
import thesis.domain.employee.model.EmployeeProjectDTO;
import thesis.domain.mapper.MapStructConfig;

@Mapper(config = MapStructConfig.class)
public interface EmployeeProjectDTOMapper {

    EmployeeProjectDTOMapper INSTANCE = Mappers.getMapper(EmployeeProjectDTOMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    EmployeeProjectDTO map(Project project);


}
