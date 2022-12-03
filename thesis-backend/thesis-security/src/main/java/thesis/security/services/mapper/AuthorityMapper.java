package thesis.security.services.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import thesis.data.role.model.RolePrivilege;

@Mapper(config = MapStructSecurityConfig.class)
public interface AuthorityMapper {

    @Mapping(target = "role", source = "privilege.name")
    SimpleGrantedAuthority map(RolePrivilege rolePrivilege);
}
