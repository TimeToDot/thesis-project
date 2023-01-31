package pl.thesis.domain.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.thesis.data.account.model.AccountDetails;
import pl.thesis.data.account.model.StatusType;
import pl.thesis.data.position.model.Position;
import pl.thesis.domain.employee.model.EmployeeDTO;
import pl.thesis.domain.employee.model.EmployeeUpdatePayloadDTO;
import pl.thesis.domain.employee.model.SimplePositionDTO;
import pl.thesis.domain.mapper.MapStructConfig;
import pl.thesis.security.services.model.ContractTypeDTO;

@Mapper(config = MapStructConfig.class)
public interface EmployeeDTOMapper {

    EmployeeDTOMapper INSTANCE = Mappers.getMapper(EmployeeDTOMapper.class);

    @Mapping(target = "id", source = "account.id")
    @Mapping(target = "firstName", source = "name")
    @Mapping(target = "middleName", source = "middleName")
    @Mapping(target = "lastName", source = "surname")
    @Mapping(target = "sex", source = "sex")
    @Mapping(target = "birthDate", source = "birthDate")
    @Mapping(target = "birthPlace", source = "birthPlace")
    @Mapping(target = "pesel", source = "pesel")
    @Mapping(target = "street", source = "street")
    @Mapping(target = "houseNumber", source = "houseNumber")
    @Mapping(target = "apartmentNumber", source = "apartmentNumber")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "postalCode", source = "postalCode")
    @Mapping(target = "country", source = "country")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "position", expression = "java(getPosition(details.getAccount().getPosition()))")
    @Mapping(target = "employmentDate", source = "employmentDate")
    @Mapping(target = "exitDate", source = "exitDate")
    @Mapping(target = "contractType", source = "contractType")
    //@Mapping(target = "billingPeriod", expression = "java(getBillingPeriod(details.getBillingPeriod()))")
    @Mapping(target = "workingTime", source = "workingTime")
    @Mapping(target = "wage", source = "wage")
    @Mapping(target = "payday", source = "payday")
    @Mapping(target = "accountNumber", source = "taxNumber")
    @Mapping(target = "email", source = "account.email")
    @Mapping(target = "password", source = "account.pass")
    @Mapping(target = "imagePath", source = "imagePath")
    @Mapping(target = "active", expression = "java(isActive(details.getAccount().getStatus()))")

    EmployeeDTO map(AccountDetails details);

    @Mapping(target = "name", source = "firstName")
    @Mapping(target = "surname", source = "lastName")
    @Mapping(target = "middleName", source = "middleName")
    @Mapping(target = "sex", source = "sex")
    @Mapping(target = "birthDate", source = "birthDate")
    @Mapping(target = "birthPlace", source = "birthPlace")
    @Mapping(target = "employmentDate", source = "employmentDate")
    @Mapping(target = "exitDate", source = "exitDate")
    @Mapping(target = "apartmentNumber", source = "apartmentNumber")
    @Mapping(target = "houseNumber", source = "houseNumber")
    @Mapping(target = "street", source = "street")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "postalCode", source = "postalCode")
    @Mapping(target = "country", source = "country")
    @Mapping(target = "pesel", source = "pesel")
    @Mapping(target = "taxNumber", source = "accountNumber")
    @Mapping(target = "idCardNumber", source = "idCardNumber")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "privateEmail", source = "privateEmail")
    @Mapping(target = "workingTime", source = "workingTime")
    @Mapping(target = "wage", source = "wage")
    @Mapping(target = "payday", source = "payday")
    @Mapping(target = "contractType", source = "contractType")
    AccountDetails mapToDetails(EmployeeUpdatePayloadDTO payloadDTO);

    default boolean isActive(StatusType type){
        return type.compareTo(StatusType.ENABLE) == 0;
    }

    default ContractTypeDTO getContractType(String type){
        if (type == null){
            return null;
        }

        return ContractTypeDTO.fromValue(type);
    }

    default SimplePositionDTO getPosition(Position position){
        if (position == null){
            return null;
        }
        return SimplePositionDTO.builder()
                .id(position.getId())
                .name(position.getName())
                .build();
    }

}
