package pl.thesis.api.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.thesis.api.auth.model.AuthenticationResponse;
import pl.thesis.security.converter.IdConverter;
import pl.thesis.api.employee.model.temp.AuthorizationTemp;
import pl.thesis.domain.mapper.MapStructConfig;
import pl.thesis.security.services.model.AuthenticationSecurity;
import pl.thesis.security.services.model.AuthorizationSecurity;

@Mapper(
        config = MapStructConfig.class,
        uses = IdConverter.class

)
public interface AuthMapper {


    @Mapping(target = "id", source = "id", qualifiedByName = {"mapToText"})
    @Mapping(target = "projectPrivileges", source = "projectPrivileges", qualifiedByName = {"mapProjectPrivileges"})
    AuthenticationResponse map(AuthenticationSecurity authenticationSecurity);

    @Mapping(target = "positionId", source = "position.id")
    AuthorizationSecurity mapToAuthorizationDTO(AuthorizationTemp authorizationPayload);



}
