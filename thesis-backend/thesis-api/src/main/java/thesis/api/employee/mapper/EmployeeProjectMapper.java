package thesis.api.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import thesis.api.employee.model.project.EmployeeProjectDetailsResponse;
import thesis.api.employee.model.project.EmployeeProjectResponse;
import thesis.api.employee.model.project.EmployeeProjectToApproveResponse;
import thesis.api.employee.model.project.temp.EmployeeProjectTempResponse;
import thesis.api.employee.model.project.temp.ProjectTempResponse;
import thesis.data.project.model.AccountProject;
import thesis.data.project.model.ProjectType;
import thesis.domain.employee.model.EmployeeProjectDTO;
import thesis.domain.employee.model.EmployeeProjectDetailsDTO;
import thesis.domain.employee.model.EmployeeProjectToApproveDTO;
import thesis.domain.mapper.MapStructConfig;

@Mapper(config = MapStructConfig.class)
public interface EmployeeProjectMapper {

    EmployeeProjectMapper INSTANCE = Mappers.getMapper(EmployeeProjectMapper.class);

    @Mapping(target = "image", source = "imagePath")
    EmployeeProjectResponse map(EmployeeProjectDTO employeeDTO);

    @Mapping(target = "imagePath", source = "imagePath")
    EmployeeProjectDetailsResponse detailsMap(EmployeeProjectDetailsDTO employeeDTO);


    @Mapping(target = "project", expression = "java(getProject(employeeDTO))")
    @Mapping(target = "joinDate", source = "joinDate")
    @Mapping(target = "exitDate", source = "exitDate")
    @Mapping(target = "employeeId", source = "projectEmployeeId")
    EmployeeProjectTempResponse tempDetailsMap(EmployeeProjectDetailsDTO employeeDTO);

    @Mapping(target = "project.image", source = "project.imagePath")
    EmployeeProjectToApproveResponse toApproveMap(EmployeeProjectToApproveDTO projectToApproveDTO);


    default ProjectTempResponse getProject(EmployeeProjectDetailsDTO detailsDTO){
        return ProjectTempResponse.builder()
                .id(detailsDTO.id())
                .name(detailsDTO.name())
                .image(detailsDTO.imagePath())
                .active(detailsDTO.active())
                .build();
    }

}
