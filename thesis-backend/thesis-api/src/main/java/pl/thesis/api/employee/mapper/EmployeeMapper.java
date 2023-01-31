package pl.thesis.api.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.thesis.api.converter.UuidConverter;
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
                UuidConverter.class
        }
)
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mapping(target = "id", source = "id", qualifiedByName = {"mapToText"})
    EmployeeResponse map(EmployeeDTO employeeDTO);

    @Mapping(target = "id", source = "id", qualifiedByName = {"mapToId"})
    EmployeeUpdatePayloadDTO mapToEmployeeUpdatePayloadDto(EmployeeUpdatePayload payload, String id);

    @Mapping(target = "id", source = "id", qualifiedByName = {"mapToId"})
    PasswordUpdatePayloadDTO mapToPasswordUpdatePayloadDto(EmployeePasswordPayload payload, String id);
}
