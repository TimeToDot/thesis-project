package thesis.domain.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(unmappedSourcePolicy = ReportingPolicy.ERROR, componentModel = "spring")
public class MapStructConfig {
}