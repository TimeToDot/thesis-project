package thesis.security.services.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import thesis.data.role.model.RolePrivilege;
import thesis.security.services.model.UserPrivilege;

@Mapper(config = MapStructSecurityConfig.class)
public interface UserPrivilegeMapper {

    @Mapping(target = "name", source = "privilege.name")
    UserPrivilege map(RolePrivilege rolePrivilege);
}
