package thesis.security.services.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(unmappedSourcePolicy = ReportingPolicy.WARN, componentModel = "spring")
public class MapStructSecurityConfig {
}
