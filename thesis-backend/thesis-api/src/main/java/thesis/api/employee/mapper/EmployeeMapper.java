package thesis.api.employee.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import thesis.api.employee.model.EmployeeResponse;
import thesis.security.services.model.ContractTypeDTO;
import thesis.domain.employee.model.EmployeeDTO;
import thesis.domain.mapper.MapStructConfig;

@Mapper(config = MapStructConfig.class)
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    //@Mapping(target = "contractType", expression = "java(getContractType(employeeDTO.contractType()))")
    //@Mapping(target = "billingPeriod", expression = "java(getBillingPeriod(employeeDTO.billingPeriod()))")
    EmployeeResponse map(EmployeeDTO employeeDTO);

    default ContractTypeDTO getContractType(ContractTypeDTO type){
        if (type == null){
            return null;
        }

        return ContractTypeDTO.fromValue(type.label);
    }

/*    default BillingPeriodDTO getBillingPeriod(BillingPeriodDTO period){
        if (period == null){
            return null;
        }

        return BillingPeriodDTO.fromValue(period.label);
    }*/
}
