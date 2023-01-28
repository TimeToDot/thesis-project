package thesis.api.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import thesis.api.employee.model.task.temp.EmployeeTaskTemp;
import thesis.domain.employee.model.EmployeeTaskDeletePayloadDTO;
import thesis.domain.mapper.MapStructConfig;

@Mapper(config = MapStructConfig.class)
public interface EmployeeTaskDeletePayloadMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "status", source = "status")
    EmployeeTaskDeletePayloadDTO mapTemp(EmployeeTaskTemp task);

}
