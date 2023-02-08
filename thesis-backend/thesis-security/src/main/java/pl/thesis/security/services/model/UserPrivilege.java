package pl.thesis.security.services.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@Setter
@EqualsAndHashCode
public class UserPrivilege {

    private SimpleGrantedAuthority name;
}
