package thesis.domain.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import thesis.data.task.model.Task;
import thesis.domain.employee.model.EmployeeTaskDTO;
import thesis.domain.mapper.MapStructConfig;
import thesis.domain.task.mapper.TaskFormDTOMapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Mapper(
        config = MapStructConfig.class,
        uses = {
                EmployeeProjectDTOMapper.class,
                TaskFormDTOMapper.class
        }
)
public interface EmployeeTaskDTOMapper {

    EmployeeTaskDTOMapper INSTANCE = Mappers.getMapper(EmployeeTaskDTOMapper.class);


    @Mapping(target = "id", source = "id")
    @Mapping(target = "employeeId", source = "account.id")
    @Mapping(target = "startDate", source = "dateFrom")
    @Mapping(target = "endDate", source = "dateTo")
/*    @Mapping(target = "startTime", expression = "java(convertToLocalDateTime(dateFrom))")
    @Mapping(target = "endTime", expression = "java(convertToLocalDateTime(dateTo))")*/
    @Mapping(target = "project", source = "form.project")
    @Mapping(target = "task", source = "form")
    @Mapping(target = "status", source = "status")
    EmployeeTaskDTO map(Task task);

/*    default LocalDateTime convertToLocalDateTime(Date date){
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }*/

}