package thesis.api.employee.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import thesis.api.employee.model.task.TaskResponse;
import thesis.domain.mapper.MapStructConfig;
import thesis.domain.task.model.TaskFormDto;

@Mapper(
        config = MapStructConfig.class
)
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    TaskResponse map(TaskFormDto taskFormDto);
}
