package thesis.api.employee.mapper;

import org.mapstruct.Mapper;
import thesis.api.employee.model.EmployeesResponse;
import thesis.domain.employee.model.EmployeesDTO;
import thesis.domain.mapper.MapStructConfig;

@Mapper(
        config = MapStructConfig.class,
        uses = EmployeeMapper.class
)
public interface EmployeesMapper {

    EmployeesResponse map(EmployeesDTO employeesDTO);
}
