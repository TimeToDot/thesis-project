package pl.thesis.domain.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.thesis.data.task.model.TaskForm;
import pl.thesis.domain.mapper.MapStructConfig;
import pl.thesis.domain.project.model.task.ProjectTaskDetailsDTO;

@Mapper(config = MapStructConfig.class)
public interface ProjectTaskDetailsDTOMapper {

    ProjectTaskDetailsDTOMapper INSTANCE = Mappers.getMapper(ProjectTaskDetailsDTOMapper.class);

    @Mapping(target = "id", source = "taskForm.id")
    @Mapping(target = "name", source = "taskForm.name")
    @Mapping(target = "description", source = "taskForm.description")
    @Mapping(target = "creationDate", source = "taskForm.createdAt")
    @Mapping(target = "archiveDate", source = "taskForm.archiveDate")
    @Mapping(target = "projectId", source = "taskForm.project.id")
    ProjectTaskDetailsDTO mapToProjectTaskDetailsDTO(TaskForm taskForm);


}
