package pl.thesis.api.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.thesis.api.converter.IdConverter;
import pl.thesis.api.converter.model.ThesisId;
import pl.thesis.api.employee.model.EmployeePasswordPayload;
import pl.thesis.api.employee.model.EmployeeResponse;
import pl.thesis.api.employee.model.EmployeeUpdatePayload;
import pl.thesis.api.global.GlobalMapper;
import pl.thesis.api.position.PositionMapper;
import pl.thesis.domain.employee.model.EmployeeDTO;
import pl.thesis.domain.employee.model.EmployeeUpdatePayloadDTO;
import pl.thesis.domain.employee.model.PasswordUpdatePayloadDTO;
import pl.thesis.domain.mapper.MapStructConfig;

@Mapper(
        config = MapStructConfig.class,
        uses = {
                PositionMapper.class,
                GlobalMapper.class,
                IdConverter.class
        }
)
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mapping(target = "id", source = "id", qualifiedByName = {"mapToText"})
    EmployeeResponse map(EmployeeDTO employeeDTO);

    @Mapping(target = "id", source = "employee.id")
    EmployeeUpdatePayloadDTO mapToEmployeeUpdatePayloadDto(EmployeeUpdatePayload payload, ThesisId employee);

    @Mapping(target = "id", source = "employee.id")
    PasswordUpdatePayloadDTO mapToPasswordUpdatePayloadDto(EmployeePasswordPayload payload, ThesisId employee);
}
