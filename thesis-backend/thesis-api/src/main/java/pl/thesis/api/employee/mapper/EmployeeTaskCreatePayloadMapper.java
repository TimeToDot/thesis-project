package pl.thesis.api.employee.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.thesis.api.converter.UuidConverter;
import pl.thesis.api.employee.model.task.EmployeeTaskCreatePayload;
import pl.thesis.api.employee.model.task.temp.EmployeeTaskTemp;
import pl.thesis.domain.employee.model.EmployeeTaskCreatePayloadDTO;
import pl.thesis.domain.mapper.MapStructConfig;

import java.time.*;
import java.util.Date;

@Mapper(
        config = MapStructConfig.class,
        uses = UuidConverter.class
)
public interface EmployeeTaskCreatePayloadMapper {
    EmployeeTaskCreatePayloadMapper INSTANCE = Mappers.getMapper(EmployeeTaskCreatePayloadMapper.class);

    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "projectId", source = "projectId", qualifiedByName = {"mapToId"})
    @Mapping(target = "taskId", source = "taskId", qualifiedByName = {"mapToId"})
    EmployeeTaskCreatePayloadDTO map(EmployeeTaskCreatePayload task);


    @Mapping(target = "startDate", expression = "java(getDate(task.startDate(), task.startTime()))")
    @Mapping(target = "endDate", expression = "java(getDate(task.endDate(), task.endTime()))")
    @Mapping(target = "projectId", source = "project.id", qualifiedByName = {"mapToId"})
    @Mapping(target = "taskId", source = "task.id", qualifiedByName = {"mapToId"})
    EmployeeTaskCreatePayloadDTO mapTemp(EmployeeTaskTemp task);

    default Date getDate(LocalDate localDate, LocalTime localTime){
        var ldt = LocalDateTime.of(
                localDate.getYear(),
                localDate.getMonth().getValue(),
                localDate.getDayOfMonth(),
                localTime.getHour(),
                localTime.getMinute()
        );



        var zdt = ZonedDateTime.of(localDate, localTime, ZoneId.systemDefault());

        var instant = ldt.toInstant(ZoneOffset.UTC);
        var date = Date.from(zdt.toInstant());

        return date;
    }

}
