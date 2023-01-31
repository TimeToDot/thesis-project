package pl.thesis.api.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.thesis.api.auth.model.AuthenticationResponse;
import pl.thesis.api.converter.UuidConverter;
import pl.thesis.api.employee.model.temp.AuthorizationTemp;
import pl.thesis.domain.mapper.MapStructConfig;
import pl.thesis.security.services.model.AuthenticationDTO;
import pl.thesis.security.services.model.AuthorizationDTO;

@Mapper(
        config = MapStructConfig.class,
        uses = UuidConverter.class

)
public interface AuthMapper {


    @Mapping(target = "id", source = "id", qualifiedByName = {"mapToText"})
    @Mapping(target = "projectPrivileges", source = "projectPrivileges", qualifiedByName = {"mapProjectPrivileges"})
    AuthenticationResponse map(AuthenticationDTO authenticationDTO);

    @Mapping(target = "positionId", source = "position.id")
    AuthorizationDTO mapToAuthorizationDTO(AuthorizationTemp authorizationPayload);



}
