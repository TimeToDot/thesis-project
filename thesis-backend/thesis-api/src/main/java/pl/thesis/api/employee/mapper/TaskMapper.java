package pl.thesis.api.employee.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.thesis.api.converter.UuidConverter;
import pl.thesis.api.employee.model.task.TaskResponse;
import pl.thesis.api.employee.model.task.temp.TaskTemp;
import pl.thesis.domain.mapper.MapStructConfig;
import pl.thesis.domain.task.model.TaskFormDto;

@Mapper(
        config = MapStructConfig.class,
        uses = {
                UuidConverter.class
        }
)
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(target = "id", source = "id", qualifiedByName = {"mapToText"})
    @Mapping(target = "projectId", source = "projectId", qualifiedByName = {"mapToText"})
    TaskResponse map(TaskFormDto dto);

    @Mapping(target = "id", source = "id", qualifiedByName = {"mapToText"})
    @Mapping(target = "projectId", source = "projectId", qualifiedByName = {"mapToText"})
    TaskTemp mapTemp(TaskFormDto dto);
}
