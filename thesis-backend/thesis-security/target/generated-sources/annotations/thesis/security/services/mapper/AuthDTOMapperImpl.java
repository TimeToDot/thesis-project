package thesis.security.services.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import thesis.data.account.model.Account;
import thesis.security.services.model.AuthenticationDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-15T17:16:21+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Private Build)"
)
@Component
public class AuthDTOMapperImpl implements AuthDTOMapper {

    @Override
    public AuthenticationDTO map(Account account) {
        if ( account == null ) {
            return null;
        }

        AuthenticationDTO.AuthenticationDTOBuilder authenticationDTO = AuthenticationDTO.builder();

        authenticationDTO.id( account.getId() );
        authenticationDTO.email( account.getEmail() );

        return authenticationDTO.build();
    }
}
