package pl.thesis.api.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.thesis.api.employee.model.task.EmployeeTasksResponse;
import pl.thesis.api.employee.model.task.temp.EmployeeTasksTemp;
import pl.thesis.domain.employee.model.EmployeeTasksDTO;
import pl.thesis.domain.mapper.MapStructConfig;

@Mapper(
        config = MapStructConfig.class,
        uses = EmployeeTaskMapper.class
)
public interface EmployeeTasksMapper {

    @Mapping(target = "tasks", source = "tasks")
    EmployeeTasksResponse map (EmployeeTasksDTO employeeTasksDTO);

    EmployeeTasksTemp mapTemp(EmployeeTasksDTO employeeTasksDTO);


}
