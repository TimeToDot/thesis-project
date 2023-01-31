package pl.thesis.domain.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.thesis.data.task.model.Task;
import pl.thesis.domain.employee.model.EmployeeTaskDTO;
import pl.thesis.domain.mapper.MapStructConfig;
import pl.thesis.domain.task.mapper.TaskFormDTOMapper;

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
    @Mapping(target = "project", source = "form.project")
    @Mapping(target = "task", source = "form")
    @Mapping(target = "status", source = "status")
    EmployeeTaskDTO map(Task task);


}