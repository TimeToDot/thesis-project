package thesis.api.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import thesis.api.employee.model.task.EmployeeTaskResponse;
import thesis.domain.employee.model.EmployeeTaskDTO;
import thesis.domain.mapper.MapStructConfig;

@Mapper(
        config = MapStructConfig.class,
        uses =   {
                EmployeeProjectMapper.class,
                TaskMapper.class
        }
)
public interface EmployeeTaskMapper {

    EmployeeTaskMapper INSTANCE = Mappers.getMapper(EmployeeTaskMapper.class);

    EmployeeTaskResponse map(EmployeeTaskDTO employeeDTO);
}
