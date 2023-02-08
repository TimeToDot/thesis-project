package pl.thesis.security.services.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.thesis.data.account.model.Account;
import pl.thesis.security.services.model.AuthenticationSecurity;

@Mapper(config = MapStructSecurityConfig.class)
public interface AuthDTOMapper {

    AuthDTOMapper INSTANCE = Mappers.getMapper(AuthDTOMapper.class);

    AuthenticationSecurity map(Account account);
}
