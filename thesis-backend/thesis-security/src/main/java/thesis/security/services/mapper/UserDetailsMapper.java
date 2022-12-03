package thesis.security.services.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import thesis.data.account.model.Account;
import thesis.security.services.model.UserDetailsDefault;

@Mapper(config = MapStructSecurityConfig.class, uses = UserRoleMapper.class)
public interface UserDetailsMapper {

    UserDetailsMapper INSTANCE = Mappers.getMapper(UserDetailsMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "login")
    @Mapping(target = "password", source = "pass")
    @Mapping(target = "email", source = "details.email")
    @Mapping(target = "authorities", source = "accountRoleList")
    @Mapping(target = "status", source = "status")
    UserDetailsDefault map(Account account);
}
