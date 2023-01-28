package thesis.api.employee.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import thesis.api.employee.model.task.EmployeeTaskCreatePayload;
import thesis.api.employee.model.task.temp.EmployeeTaskTemp;
import thesis.domain.employee.model.EmployeeTaskCreatePayloadDTO;
import thesis.domain.mapper.MapStructConfig;

import java.time.*;
import java.util.Date;

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


    @Mapping(target = "startDate", expression = "java(getDate(task.startDate(), task.startTime()))")
    @Mapping(target = "endDate", expression = "java(getDate(task.endDate(), task.endTime()))")
    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "taskId", source = "task.id")
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
