package thesis.api.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import thesis.api.employee.model.task.EmployeeTasksResponse;
import thesis.api.employee.model.task.temp.EmployeeTasksTemp;
import thesis.domain.employee.model.EmployeeTasksDTO;
import thesis.domain.mapper.MapStructConfig;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Mapper(
        config = MapStructConfig.class,
        uses = EmployeeTaskMapper.class
)
public interface EmployeeTasksMapper {

    @Mapping(target = "tasks", source = "tasks")
    EmployeeTasksResponse map (EmployeeTasksDTO employeeTasksDTO);

    EmployeeTasksTemp mapTemp(EmployeeTasksDTO employeeTasksDTO);


}
