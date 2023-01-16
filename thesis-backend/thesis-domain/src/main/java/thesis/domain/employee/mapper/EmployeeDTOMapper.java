package thesis.domain.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import thesis.data.account.model.AccountDetails;
import thesis.data.account.model.BillingPeriod;
import thesis.data.account.model.ContractType;
import thesis.data.account.model.StatusType;
import thesis.domain.employee.model.BillingPeriodDTO;
import thesis.domain.employee.model.ContractTypeDTO;
import thesis.domain.employee.model.EmployeeDTO;
import thesis.domain.mapper.MapStructConfig;

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
    @Mapping(target = "positionId", source = "account.position.id")
    @Mapping(target = "employmentDate", source = "employmentDate")
    @Mapping(target = "exitDate", source = "exitDate")
    @Mapping(target = "contractType", expression = "java(getContractType(details.getContractType()))")
    @Mapping(target = "billingPeriod", expression = "java(getBillingPeriod(details.getBillingPeriod()))")
    @Mapping(target = "workingTime", source = "workingTime")
    @Mapping(target = "wage", source = "wage")
    @Mapping(target = "payday", source = "payday")
    @Mapping(target = "accountNumber", source = "taxNumber")
    @Mapping(target = "email", source = "account.email")
    @Mapping(target = "password", source = "account.pass")
    @Mapping(target = "imagePath", source = "imagePath")
    @Mapping(target = "active", expression = "java(isActive(details.getAccount().getStatus()))")

    EmployeeDTO map(AccountDetails details);

    default boolean isActive(StatusType type){
        return type.compareTo(StatusType.ENABLE) == 0;
    }

    default ContractTypeDTO getContractType(ContractType type){
        if (type == null){
            return null;
        }

        return ContractTypeDTO.valueOf(type.label);
    }

    default BillingPeriodDTO getBillingPeriod(BillingPeriod period){
        if (period == null){
            return null;
        }

        return BillingPeriodDTO.valueOf(period.label);
    }

}
