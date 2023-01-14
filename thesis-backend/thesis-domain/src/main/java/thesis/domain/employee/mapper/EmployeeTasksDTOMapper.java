package thesis.domain.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import thesis.data.task.model.Task;
import thesis.domain.employee.model.EmployeeTaskDTO;
import thesis.domain.employee.model.EmployeeTasksDTO;
import thesis.domain.mapper.MapStructConfig;

import java.util.List;

@Mapper(
        config = MapStructConfig.class,
        uses = EmployeeTaskDTOMapper.class
)
public interface EmployeeTasksDTOMapper {


    List<EmployeeTaskDTO> map(List<Task> task);
}
