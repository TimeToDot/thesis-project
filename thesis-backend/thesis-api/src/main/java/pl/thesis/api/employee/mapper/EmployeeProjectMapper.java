package pl.thesis.api.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.thesis.security.converter.IdConverter;
import pl.thesis.security.services.model.ThesisId;
import pl.thesis.api.employee.model.project.EmployeeProjectDetailsResponse;
import pl.thesis.api.employee.model.project.EmployeeProjectResponse;
import pl.thesis.api.employee.model.project.EmployeeProjectsToApprovePayload;
import pl.thesis.api.employee.model.project.temp.EmployeeProjectTempResponse;
import pl.thesis.api.employee.model.temp.EmployeeProjectsApproveTempPayload;
import pl.thesis.domain.employee.model.EmployeeProjectDTO;
import pl.thesis.domain.employee.model.EmployeeProjectDetailsDTO;
import pl.thesis.domain.employee.model.EmployeeProjectsApproveTemp;
import pl.thesis.domain.mapper.MapStructConfig;

@Mapper(
        config = MapStructConfig.class,
        uses = IdConverter.class
)
public interface EmployeeProjectMapper {

    //EmployeeProjectMapper INSTANCE = Mappers.getMapper(EmployeeProjectMapper.class);

    @Mapping(target = "id", source = "id", qualifiedByName = {"mapToText"})
    @Mapping(target = "image", source = "imagePath")
    EmployeeProjectResponse map(EmployeeProjectDTO employeeDTO);

    @Mapping(target = "id", source = "id", qualifiedByName = {"mapToText"})
    @Mapping(target = "imagePath", source = "imagePath")
    EmployeeProjectDetailsResponse detailsMap(EmployeeProjectDetailsDTO employeeDTO);


    @Mapping(target = "project.id", source = "id", qualifiedByName = {"mapToText"})
    @Mapping(target = "project.image", source = "imagePath")
    @Mapping(target = "project.name", source = "name")
    @Mapping(target = "project.active", source = "active")
    @Mapping(target = "joinDate", source = "joinDate")
    @Mapping(target = "exitDate", source = "exitDate")
    @Mapping(target = "employeeId", source = "projectEmployeeId", qualifiedByName = {"mapToText"})
    EmployeeProjectTempResponse tempDetailsMap(EmployeeProjectDetailsDTO employeeDTO);

    @Mapping(target = "id", source = "employee.id")
    @Mapping(target = "projects", source = "payload.projects", qualifiedByName = {"mapToIdList"})
    EmployeeProjectsApproveTemp mapToEmployeeProjectsApproveTemp(EmployeeProjectsApproveTempPayload payload, ThesisId employee);

    EmployeeProjectsApproveTemp mapToDTO(EmployeeProjectsToApprovePayload payload, Long id);

}
