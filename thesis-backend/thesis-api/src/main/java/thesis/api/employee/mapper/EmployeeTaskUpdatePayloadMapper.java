package thesis.api.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import thesis.api.employee.model.task.EmployeeTaskUpdatePayload;
import thesis.domain.employee.model.EmployeeTaskUpdatePayloadDTO;
import thesis.domain.mapper.MapStructConfig;

@Mapper(config = MapStructConfig.class)
public interface EmployeeTaskUpdatePayloadMapper {

    EmployeeTaskUpdatePayloadMapper INSTANCE = Mappers.getMapper(EmployeeTaskUpdatePayloadMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
/*    @Mapping(target = "startTime", expression = "startTime")
    @Mapping(target = "endTime", expression = "endTime")*/
    @Mapping(target = "projectId", source = "projectId")
    @Mapping(target = "taskId", source = "taskId")
    @Mapping(target = "status", source = "status")
    EmployeeTaskUpdatePayloadDTO map(EmployeeTaskUpdatePayload task);
}
