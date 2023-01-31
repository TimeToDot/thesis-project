package pl.thesis.api.global;

import org.mapstruct.Mapper;
import pl.thesis.api.global.model.BillingPeriodResponse;
import pl.thesis.api.global.model.ContractResponse;
import pl.thesis.domain.employee.model.BillingPeriodDTO;
import pl.thesis.domain.mapper.MapStructConfig;
import pl.thesis.security.services.model.ContractDTO;
import pl.thesis.security.services.model.ContractTypeDTO;

@Mapper(config = MapStructConfig.class)
public interface GlobalMapper {

    ContractResponse mapToContractTypeResponse(ContractDTO dto);

    BillingPeriodResponse mapToBillingPeriodResponse(BillingPeriodDTO dto);
}
