package thesis.api.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import thesis.api.employee.model.EmployeeResponse;
import thesis.domain.employee.model.EmployeeDTO;
import thesis.domain.mapper.MapStructConfig;

@Mapper(config = MapStructConfig.class)
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeResponse map(EmployeeDTO employeeDTO);
}
