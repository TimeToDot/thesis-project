package thesis.api.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import thesis.api.auth.model.AuthenticationResponse;
import thesis.api.auth.model.AuthorizationPayload;
import thesis.data.account.model.BillingPeriod;
import thesis.data.account.model.ContractType;
import thesis.domain.employee.model.BillingPeriodDTO;
import thesis.domain.employee.model.ContractTypeDTO;
import thesis.security.services.model.AuthenticationDTO;
import thesis.security.services.model.AuthorizationDTO;
import thesis.domain.mapper.MapStructConfig;
import thesis.security.services.model.BillingPeriodSecurity;
import thesis.security.services.model.ContractTypeSecurity;

@Mapper(config = MapStructConfig.class)
public interface AuthMapper {


    AuthenticationResponse map(AuthenticationDTO authenticationDTO);

    @Mapping(target = "contractType", expression = "java(getContractType(authorizationPayload.getContractType()))")
    @Mapping(target = "billingPeriod", expression = "java(getBillingPeriod(authorizationPayload.getBillingPeriod()))")
    AuthorizationDTO mapToAuthorizationDTO(AuthorizationPayload authorizationPayload);

    default ContractTypeSecurity getContractType(ContractTypeDTO type){
        if (type == null){
            return null;
        }

        return ContractTypeSecurity.valueOf(type.label);
    }

    default BillingPeriodSecurity getBillingPeriod(BillingPeriodDTO period){
        if (period == null){
            return null;
        }

        return BillingPeriodSecurity.valueOf(period.label);
    }
}
