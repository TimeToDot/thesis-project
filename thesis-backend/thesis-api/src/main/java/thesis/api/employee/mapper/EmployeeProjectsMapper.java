package thesis.api.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import thesis.api.employee.model.EmployeeProjectsResponse;
import thesis.domain.employee.model.EmployeeProjectsDTO;
import thesis.domain.mapper.MapStructConfig;

@Mapper(
        config = MapStructConfig.class,
        uses = EmployeeProjectMapper.class
)
public interface EmployeeProjectsMapper {

    @Mapping(target = "projects", source = "employeeProjectDTOList")
    @Mapping(target = "paging", source = "paging")
    @Mapping(target = "sorting", source = "sorting")
    EmployeeProjectsResponse map (EmployeeProjectsDTO employeeProjectsDTO);
}
