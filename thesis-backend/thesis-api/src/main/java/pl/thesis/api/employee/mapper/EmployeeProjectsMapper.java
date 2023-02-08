package pl.thesis.api.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.thesis.api.employee.model.project.EmployeeProjectsResponse;
import pl.thesis.api.employee.model.project.EmployeeProjectsToApproveResponse;
import pl.thesis.api.employee.model.project.temp.EmployeeProjectsTempResponse;
import pl.thesis.domain.employee.model.EmployeeProjectsDTO;
import pl.thesis.domain.employee.model.EmployeeProjectsToApproveDTO;
import pl.thesis.domain.mapper.MapStructConfig;

@Mapper(
        config = MapStructConfig.class,
        uses = EmployeeProjectMapper.class
)
public interface EmployeeProjectsMapper {

    @Mapping(target = "projects", source = "employeeProjectDTOList")
    @Mapping(target = "paging", source = "paging")
    @Mapping(target = "sorting", source = "sorting")
    EmployeeProjectsResponse map (EmployeeProjectsDTO employeeProjectsDTO);

    @Mapping(target = "projects", source = "employeeProjectDTOList")
    @Mapping(target = "paging", source = "paging")
    @Mapping(target = "sorting", source = "sorting")
    EmployeeProjectsTempResponse mapTemp (EmployeeProjectsDTO employeeProjectsDTO);

    @Mapping(target = "projects", source = "projects")
    EmployeeProjectsToApproveResponse toApproveMap (EmployeeProjectsToApproveDTO employeeProjectsDTO);
}
