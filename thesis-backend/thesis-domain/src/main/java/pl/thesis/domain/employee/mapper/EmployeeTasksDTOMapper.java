package pl.thesis.domain.employee.mapper;

import org.mapstruct.Mapper;
import pl.thesis.data.task.model.Task;
import pl.thesis.domain.employee.model.EmployeeTaskDTO;
import pl.thesis.domain.mapper.MapStructConfig;

import java.util.List;

@Mapper(
        config = MapStructConfig.class,
        uses = EmployeeTaskDTOMapper.class
)
public interface EmployeeTasksDTOMapper {


    List<EmployeeTaskDTO> map(List<Task> task);
}
