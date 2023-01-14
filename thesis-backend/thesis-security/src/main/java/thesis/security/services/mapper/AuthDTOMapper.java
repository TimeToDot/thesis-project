package thesis.security.services.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import thesis.data.account.model.Account;
import thesis.security.services.model.AuthenticationDTO;

@Mapper(config = MapStructSecurityConfig.class)
public interface AuthDTOMapper {

    AuthDTOMapper INSTANCE = Mappers.getMapper(AuthDTOMapper.class);

    AuthenticationDTO map(Account account);
}
