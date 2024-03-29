package pl.thesis.api.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.thesis.security.converter.IdConverter;
import pl.thesis.api.employee.model.task.temp.EmployeeTaskTemp;
import pl.thesis.domain.employee.model.EmployeeTaskDeletePayloadDTO;
import pl.thesis.domain.mapper.MapStructConfig;

@Mapper(
        config = MapStructConfig.class,
        uses = IdConverter.class
)
public interface EmployeeTaskDeletePayloadMapper {

    @Mapping(target = "id", source = "id", qualifiedByName = {"mapToId"})
    @Mapping(target = "projectId", source = "project.id", qualifiedByName = {"mapToId"})
    @Mapping(target = "taskId", source = "task.id", qualifiedByName = {"mapToId"})
    @Mapping(target = "status", source = "status")
    EmployeeTaskDeletePayloadDTO mapTemp(EmployeeTaskTemp task);

}
