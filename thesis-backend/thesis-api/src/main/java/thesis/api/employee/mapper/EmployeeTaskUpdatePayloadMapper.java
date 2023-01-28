package thesis.api.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import thesis.api.employee.model.task.EmployeeTaskUpdatePayload;
import thesis.api.employee.model.task.temp.EmployeeTaskTemp;
import thesis.domain.employee.model.EmployeeTaskUpdatePayloadDTO;
import thesis.domain.mapper.MapStructConfig;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;

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

    @Mapping(target = "id", source = "id")
    @Mapping(target = "startDate", expression = "java(getDate(task.startDate(), task.startTime()))")
    @Mapping(target = "endDate", expression = "java(getDate(task.endDate(), task.endTime()))")
    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "status", source = "status")
    EmployeeTaskUpdatePayloadDTO mapTemp(EmployeeTaskTemp task);

    default Date getDate(LocalDate localDate, LocalTime localTime){
        var ldt = LocalDateTime.of(
                localDate.getYear(),
                localDate.getMonth().getValue(),
                localDate.getDayOfMonth(),
                localTime.getHour(),
                localTime.getMinute()
        );

        var instant = ldt.toInstant(ZoneOffset.UTC);
        var date = Date.from(instant);
        return date;
    }
}
