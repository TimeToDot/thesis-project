package thesis.security.controller;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import thesis.security.services.mapper.MapStructSecurityConfig;

@Mapper(config = MapStructSecurityConfig.class)
public interface RoleTypeMapper {

    @Mapping(target = "roleType")
    RoleTypeDto map (String role);
}
