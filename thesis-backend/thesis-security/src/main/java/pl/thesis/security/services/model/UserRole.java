package pl.thesis.security.services.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class UserRole {

    private String name;
    private List<SimpleGrantedAuthority> privilegeList;
}
