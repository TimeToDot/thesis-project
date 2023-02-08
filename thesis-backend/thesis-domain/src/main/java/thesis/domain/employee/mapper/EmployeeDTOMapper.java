package thesis.domain.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import thesis.data.account.model.AccountDetails;
import thesis.data.position.model.Position;
import thesis.domain.employee.model.EmployeeDTO;
import thesis.domain.mapper.MapStructConfig;

import java.util.List;
import java.util.UUID;

@Mapper(config = MapStructConfig.class)
public interface EmployeeDTOMapper {

    EmployeeDTOMapper INSTANCE = Mappers.getMapper(EmployeeDTOMapper.class);

    @Mapping(target = "firstName", source = "name")
    @Mapping(target = "lastName", source = "surname")
    @Mapping(target = "email", source = "account.email")
    @Mapping(target = "password", source = "account.pass")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "postalCode", source = "postalCode")
    @Mapping(target = "street", source = "street")
    @Mapping(target = "accountNumber", source = "taxNumber")
    @Mapping(target = "employmentDate", source = "createdAt")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "positionIds", source = "account.positions")
    @Mapping(target = "sex", source = "sex")
    EmployeeDTO map(AccountDetails details);

    default List<String> map(List<Position> positions) {
        return positions.stream().map(Position::getId).map(UUID::toString).toList();
    }


}
