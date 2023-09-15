package pl.thesis.domain.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.thesis.data.project.model.BillingPeriod;
import pl.thesis.domain.employee.model.BillingPeriodDTO;
import pl.thesis.domain.mapper.MapStructConfig;

@Mapper(config = MapStructConfig.class)
public interface BillingPeriodDTOMapper {

    BillingPeriodDTOMapper INSTANCE = Mappers.getMapper(BillingPeriodDTOMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    BillingPeriodDTO map(BillingPeriod billingPeriod);
}
