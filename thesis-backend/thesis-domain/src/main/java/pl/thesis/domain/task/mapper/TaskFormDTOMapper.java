package pl.thesis.domain.task.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.thesis.data.task.model.TaskForm;
import pl.thesis.domain.mapper.MapStructConfig;
import pl.thesis.domain.task.model.TaskFormDto;

@Mapper(config = MapStructConfig.class)
public interface TaskFormDTOMapper {

    TaskFormDTOMapper INSTANCE = Mappers.getMapper(TaskFormDTOMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "creationDate", source = "createdAt")
    @Mapping(target = "projectId", source = "project.id")
    TaskFormDto map(TaskForm taskForm);
}