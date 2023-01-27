package thesis.domain.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import thesis.data.project.model.AccountProject;
import thesis.data.project.model.Project;
import thesis.data.project.model.ProjectType;
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
    @Mapping(target = "projectEmployeeId", source = "id")
    @Mapping(target = "active", expression = "java(isActive(accountProject))")
    EmployeeProjectDetailsDTO map(AccountProject accountProject);

    default Boolean isActive(AccountProject accountProject){
        return accountProject.getProject().getStatus().compareTo(ProjectType.ACTIVE) == 0;
    }

}
