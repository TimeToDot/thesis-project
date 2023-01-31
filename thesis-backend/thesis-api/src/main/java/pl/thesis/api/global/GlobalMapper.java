package pl.thesis.api.global;

import org.mapstruct.Mapper;
import pl.thesis.api.global.model.BillingPeriodResponse;
import pl.thesis.api.global.model.ContractResponse;
import pl.thesis.domain.employee.model.BillingPeriodDTO;
import pl.thesis.domain.employee.model.ContractDTO;
import pl.thesis.domain.mapper.MapStructConfig;

@Mapper(config = MapStructConfig.class)
public interface GlobalMapper {

    ContractResponse mapToContractTypeResponse(ContractDTO dto);

    BillingPeriodResponse mapToBillingPeriodResponse(BillingPeriodDTO dto);
}
