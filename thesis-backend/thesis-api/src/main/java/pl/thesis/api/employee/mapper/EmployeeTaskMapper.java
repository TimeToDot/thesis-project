package pl.thesis.api.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.thesis.api.converter.IdConverter;
import pl.thesis.api.employee.model.task.EmployeeTaskResponse;
import pl.thesis.api.employee.model.task.temp.EmployeeTaskTemp;
import pl.thesis.api.project.mapper.ProjectMapper;
import pl.thesis.domain.employee.model.EmployeeTaskDTO;
import pl.thesis.domain.mapper.MapStructConfig;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Mapper(
        config = MapStructConfig.class,
        uses =   {
                EmployeeProjectMapper.class,
                ProjectMapper.class,
                TaskMapper.class,
                IdConverter.class
        }
)
public interface EmployeeTaskMapper {

    EmployeeTaskMapper INSTANCE = Mappers.getMapper(EmployeeTaskMapper.class);

    EmployeeTaskResponse map(EmployeeTaskDTO employeeDTO);

    @Mapping(target = "startDate", expression = "java(getLocalDate(employeeTaskDTO.startDate()))")
    @Mapping(target = "startTime", expression = "java(getLocalTime(employeeTaskDTO.startDate()))")
    @Mapping(target = "endDate", expression = "java(getLocalDate(employeeTaskDTO.endDate()))")
    @Mapping(target = "endTime", expression = "java(getLocalTime(employeeTaskDTO.endDate()))")
    @Mapping(target = "id", source = "id", qualifiedByName = {"mapToText"})
    @Mapping(target = "employeeId", source = "employeeId", qualifiedByName = {"mapToText"})
    EmployeeTaskTemp mapTemp(EmployeeTaskDTO employeeTaskDTO);

    default LocalDate getLocalDate(Date date){
        var zdt = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());

        return LocalDate.of(zdt.getYear(), zdt.getMonth().getValue(), zdt.getDayOfMonth());
    }

    default LocalTime getLocalTime(Date date){
        var zdt = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());

        return LocalTime.of(zdt.getHour(), zdt.getMinute());
    }
}
