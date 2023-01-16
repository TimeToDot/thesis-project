package thesis.api.auth.mapper;

import org.mapstruct.Mapper;
import thesis.api.auth.model.AuthenticationResponse;
import thesis.api.auth.model.AuthorizationPayload;
import thesis.security.services.model.AuthenticationDTO;
import thesis.security.services.model.AuthorizationDTO;
import thesis.domain.mapper.MapStructConfig;

@Mapper(config = MapStructConfig.class)
public interface AuthMapper {

    AuthenticationResponse map(AuthenticationDTO authenticationDTO);


    AuthorizationDTO mapToAuthorizationDTO(AuthorizationPayload authorizationPayload);
}
