package thesis.api.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import thesis.api.auth.model.AuthenticationResponse;
import thesis.api.auth.model.AuthorizationPayload;
import thesis.domain.employee.model.ContractTypeDTO;
import thesis.domain.mapper.MapStructConfig;
import thesis.security.services.model.AuthenticationDTO;
import thesis.security.services.model.AuthorizationDTO;
import thesis.security.services.model.ContractTypeSecurity;

@Mapper(config = MapStructConfig.class)
public interface AuthMapper {


    AuthenticationResponse map(AuthenticationDTO authenticationDTO);

    @Mapping(target = "contractType", expression = "java(getContractType(authorizationPayload.getContractType()))")
    AuthorizationDTO mapToAuthorizationDTO(AuthorizationPayload authorizationPayload);

    default ContractTypeSecurity getContractType(ContractTypeDTO type){
        if (type == null){
            return null;
        }

        return ContractTypeSecurity.fromValue(type.label);
    }

/*    default BillingPeriodSecurity getBillingPeriod(BillingPeriodDTO period){
        if (period == null){
            return null;
        }

        return BillingPeriodSecurity.fromValue(period.label);
    }*/
}
