package thesis.domain.employee.mapper;

import org.mapstruct.Mapper;
import thesis.data.task.model.Task;
import thesis.domain.employee.model.EmployeeTasksDTO;
import thesis.domain.mapper.MapStructConfig;
import thesis.domain.task.mapper.TaskFormDTOMapper;

import java.util.List;

@Mapper(
        config = MapStructConfig.class,
        uses = EmployeeTaskDTOMapper.class
)
public interface EmployeeTasksDTOMapper {

    EmployeeTasksDTO map(List<Task> task);
}
