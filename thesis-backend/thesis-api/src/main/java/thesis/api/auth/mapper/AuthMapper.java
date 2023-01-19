package thesis.api.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import thesis.api.auth.model.AuthenticationResponse;
import thesis.api.auth.model.AuthorizationPayload;
import thesis.api.employee.model.temp.AuthorizationTemp;
import thesis.security.services.model.*;
import thesis.domain.mapper.MapStructConfig;

@Mapper(config = MapStructConfig.class)
public interface AuthMapper {


    AuthenticationResponse map(AuthenticationDTO authenticationDTO);

    //@Mapping(target = "contractType", expression = "java(getContractType(authorizationPayload.getContractType()))")
    @Mapping(target = "positionId", source = "position.id")
    AuthorizationDTO mapToAuthorizationDTO(AuthorizationTemp authorizationPayload);

/*    default ContractDTO getContractType(ContractDTO contractDTO){
        if (contractDTO == null){
            return null;
        }

        return ContractDTO.builder()
                .id(contractDTO.id())
                .name(ContractTypeDTO.fromValue(contractDTO.name()))
                .build();
    }*/

/*    default BillingPeriodSecurity getBillingPeriod(BillingPeriodDTO period){
        if (period == null){
            return null;
        }

        return BillingPeriodSecurity.fromValue(period.label);
    }*/
}
