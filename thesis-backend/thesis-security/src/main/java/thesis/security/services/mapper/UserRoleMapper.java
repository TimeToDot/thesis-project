package thesis.security.services.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import thesis.data.account.model.AccountRole;
import thesis.security.services.model.UserRole;

@Mapper(config = MapStructSecurityConfig.class, uses = UserPrivilegeMapper.class)
public interface UserRoleMapper {

    @Mapping(target = "name", source = "role.name")
    @Mapping(target = "privilegeList", source = "role.rolePrivilegeList")
    UserRole map(AccountRole role);
}
