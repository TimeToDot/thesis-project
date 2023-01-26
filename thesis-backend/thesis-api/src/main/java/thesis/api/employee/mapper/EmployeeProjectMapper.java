package thesis.api.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import thesis.api.employee.model.project.EmployeeProjectResponse;
import thesis.api.employee.model.project.EmployeeProjectToApproveResponse;
import thesis.domain.employee.model.EmployeeProjectDTO;
import thesis.domain.employee.model.EmployeeProjectToApproveDTO;
import thesis.domain.mapper.MapStructConfig;

@Mapper(config = MapStructConfig.class)
public interface EmployeeProjectMapper {

    EmployeeProjectMapper INSTANCE = Mappers.getMapper(EmployeeProjectMapper.class);

    @Mapping(target = "image", source = "imagePath")
    EmployeeProjectResponse map(EmployeeProjectDTO employeeDTO);

    @Mapping(target = "project.image", source = "project.imagePath")
    EmployeeProjectToApproveResponse toApproveMap(EmployeeProjectToApproveDTO projectToApproveDTO);
}
