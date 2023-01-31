package pl.thesis.api.employee.mapper;

import org.mapstruct.Mapper;
import pl.thesis.api.employee.model.EmployeesResponse;
import pl.thesis.domain.employee.model.EmployeesDTO;
import pl.thesis.domain.mapper.MapStructConfig;

@Mapper(
        config = MapStructConfig.class,
        uses = EmployeeMapper.class
)
public interface EmployeesMapper {

    EmployeesResponse map(EmployeesDTO employeesDTO);
}
