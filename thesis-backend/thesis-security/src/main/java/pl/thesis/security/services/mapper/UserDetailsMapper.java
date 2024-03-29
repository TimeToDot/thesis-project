package pl.thesis.security.services.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.thesis.data.account.model.Account;
import pl.thesis.data.account.model.AccountRole;
import pl.thesis.security.services.model.UserDetailsDefault;

import java.util.ArrayList;
import java.util.Collection;

@Mapper(config = MapStructSecurityConfig.class)
public interface UserDetailsMapper {

    UserDetailsMapper INSTANCE = Mappers.getMapper(UserDetailsMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "email")
    @Mapping(target = "password", source = "pass")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "authorities", expression = "java(getAuthorities(account))")
    @Mapping(target = "status", source = "status")
    UserDetailsDefault map(Account account);


    default Collection<GrantedAuthority> getAuthorities(Account account) {

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        account.getAccountRoles().stream()
                .map(AccountRole::getRole)
                .forEach(role -> {
                    role.getPrivileges()
                            .forEach(privilege -> authorities.add(new SimpleGrantedAuthority(privilege.getName().name())));
                });

        account.getAccountProjects().forEach(projectAccountRole -> {
            var projectId = projectAccountRole.getProject().getId();

            projectAccountRole.getRole().getPrivileges().forEach(privilege -> {
                authorities.add(new SimpleGrantedAuthority(projectId.toString() + "#" + privilege.getName().name()));
            });
        });
        return authorities;
    }
}
