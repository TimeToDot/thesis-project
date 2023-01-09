package thesis.api.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import thesis.api.employee.model.task.EmployeeTasksResponse;
import thesis.domain.employee.model.EmployeeTasksDTO;
import thesis.domain.mapper.MapStructConfig;

@Mapper(
        config = MapStructConfig.class,
        uses = EmployeeTaskMapper.class
)
public interface EmployeeTasksMapper {

    @Mapping(target = "tasks", source = "tasks")
    EmployeeTasksResponse map (EmployeeTasksDTO employeeTasksDTO);
}
