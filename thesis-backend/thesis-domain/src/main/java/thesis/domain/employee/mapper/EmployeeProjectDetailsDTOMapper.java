package thesis.domain.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import thesis.data.project.model.AccountProject;
import thesis.data.project.model.Project;
import thesis.domain.employee.model.EmployeeProjectDetailsDTO;
import thesis.domain.mapper.MapStructConfig;

@Mapper(config = MapStructConfig.class)
public interface EmployeeProjectDetailsDTOMapper {
    EmployeeProjectDetailsDTOMapper INSTANCE = Mappers.getMapper(EmployeeProjectDetailsDTOMapper.class);

    @Mapping(target = "id", source = "project.id")
    @Mapping(target = "name", source = "project.name")
    @Mapping(target = "imagePath", source = "project.details.imagePath")
    @Mapping(target = "joinDate", source = "joinDate")
    @Mapping(target = "exitDate", source = "exitDate")
    EmployeeProjectDetailsDTO map(AccountProject accountProject);

}
