package thesis.api.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import thesis.api.employee.model.Project;
import thesis.domain.employee.model.EmployeeProjectDTO;
import thesis.domain.mapper.MapStructConfig;

@Mapper(config = MapStructConfig.class)
public interface EmployeeProjectMapper {

    EmployeeProjectMapper INSTANCE = Mappers.getMapper(EmployeeProjectMapper.class);

    Project map(EmployeeProjectDTO employeeDTO);
}
