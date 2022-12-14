package thesis.domain.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import thesis.data.account.model.Account;
import thesis.domain.employee.model.EmployeeDTO;
import thesis.domain.mapper.MapStructConfig;

@Mapper(config = MapStructConfig.class)
public interface EmployeeDTOMapper {

  EmployeeDTOMapper INSTANCE = Mappers.getMapper(EmployeeDTOMapper.class);

  @Mapping(target = "firstName", source = "details.name")
  @Mapping(target = "lastName", source = "details.surname")
  @Mapping(target = "email", source = "email")
  @Mapping(target = "password", source = "pass")
  @Mapping(target = "city", source = "details.city")
  @Mapping(target = "postalCode", source = "details.postalCode")
  @Mapping(target = "street", source = "details.street")
  @Mapping(target = "accountNumber", source = "details.taxNumber")
  @Mapping(target = "employmentDate", source = "details.createdAt")
  @Mapping(target = "phoneNumber", source = "details.phoneNumber")
  @Mapping(target = "positionId", source = "position.id")
  @Mapping(target = "sex", source = "details.sex")
  EmployeeDTO map(Account account);

}
