package thesis.api.employee.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import thesis.api.employee.model.task.EmployeeTaskCreatePayload;
import thesis.domain.employee.model.EmployeeTaskCreatePayloadDTO;
import thesis.domain.mapper.MapStructConfig;

@Mapper(config = MapStructConfig.class)
public interface EmployeeTaskCreatePayloadMapper {
    EmployeeTaskCreatePayloadMapper INSTANCE = Mappers.getMapper(EmployeeTaskCreatePayloadMapper.class);

    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
/*    @Mapping(target = "startTime", expression = "startTime")
    @Mapping(target = "endTime", expression = "endTime")*/
    @Mapping(target = "projectId", source = "projectId")
    @Mapping(target = "taskId", source = "taskId")
    EmployeeTaskCreatePayloadDTO map(EmployeeTaskCreatePayload task);
}
