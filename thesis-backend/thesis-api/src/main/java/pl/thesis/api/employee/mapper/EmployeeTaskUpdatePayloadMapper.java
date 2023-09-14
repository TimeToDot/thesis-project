package pl.thesis.api.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.thesis.api.converter.UuidConverter;
import pl.thesis.api.employee.model.task.EmployeeTaskUpdatePayload;
import pl.thesis.api.employee.model.task.temp.EmployeeTaskTemp;
import pl.thesis.domain.employee.model.EmployeeTaskUpdatePayloadDTO;
import pl.thesis.domain.mapper.MapStructConfig;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

@Mapper(
        config = MapStructConfig.class,
        uses = UuidConverter.class
)
public interface EmployeeTaskUpdatePayloadMapper {

    //EmployeeTaskUpdatePayloadMapper INSTANCE = Mappers.getMapper(EmployeeTaskUpdatePayloadMapper.class);

    @Mapping(target = "id", source = "task.id", qualifiedByName = {"mapToId"})
    @Mapping(target = "employeeId", source = "eid", qualifiedByName = {"mapToId"})
    @Mapping(target = "projectId", source = "task.projectId", qualifiedByName = {"mapToId"})
    @Mapping(target = "taskId", source = "task.taskId", qualifiedByName = {"mapToId"})
    EmployeeTaskUpdatePayloadDTO map(EmployeeTaskUpdatePayload task, String eid);

    @Mapping(target = "employeeId", source = "employeeId")
    @Mapping(target = "projectId", source = "task.projectId", qualifiedByName = {"mapToId"})
    @Mapping(target = "taskId", source = "task.taskId", qualifiedByName = {"mapToId"})
    EmployeeTaskUpdatePayloadDTO mapWithEid(EmployeeTaskUpdatePayload task, UUID employeeId);

    @Mapping(target = "id", source = "task.id", qualifiedByName = {"mapToId"})
    @Mapping(target = "employeeId", source = "eid", qualifiedByName = {"mapToId"})
    @Mapping(target = "startDate", expression = "java(getDate(task.startDate(), task.startTime()))")
    @Mapping(target = "endDate", expression = "java(getDate(task.endDate(), task.endTime()))")
    @Mapping(target = "projectId", source = "task.project.id", qualifiedByName = {"mapToId"})
    @Mapping(target = "taskId", source = "task.task.id", qualifiedByName = {"mapToId"})
    EmployeeTaskUpdatePayloadDTO mapTemp(EmployeeTaskTemp task, String eid);

    default Date getDate(LocalDate localDate, LocalTime localTime) {
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