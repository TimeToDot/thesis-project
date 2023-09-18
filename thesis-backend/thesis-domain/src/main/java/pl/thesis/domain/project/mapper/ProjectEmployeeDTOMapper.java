package pl.thesis.domain.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.thesis.data.project.model.AccountProject;
import pl.thesis.data.project.model.ProjectAccountStatus;
import pl.thesis.domain.mapper.MapStructConfig;
import pl.thesis.domain.project.model.employee.EmployeeDTO;
import pl.thesis.domain.project.model.employee.ProjectEmployeeDTO;

@Mapper(
        config = MapStructConfig.class,
        uses = PEmployeeDTOMapper.class
)
public interface ProjectEmployeeDTOMapper {

    @Mapping(target = "projectEmployeeId", source = "accountProject.id")
    @Mapping(target = "employee", source = "employeeDTO")
    @Mapping(target = "active", expression = "java(isActive(accountProject.getStatus()))")
    @Mapping(target = "joinDate", source = "accountProject.joinDate")
    @Mapping(target = "exitDate", source = "accountProject.exitDate")
    @Mapping(target = "workingTime", source = "accountProject.workingTime")
    ProjectEmployeeDTO map(AccountProject accountProject, EmployeeDTO employeeDTO);

    default boolean isActive(ProjectAccountStatus type){
        return type.compareTo(ProjectAccountStatus.ACTIVE) == 0;
    }
}
