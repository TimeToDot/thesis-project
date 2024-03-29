package pl.thesis.security.services.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
@Getter
@Setter
public class UserDetailsDefault implements UserDetails {

    private Long id;
    private String username;
    private String email;
    private String password;
    private Collection<GrantedAuthority> authorities;
    private UserStatusType status;

    @Override
    public boolean isAccountNonExpired() {
        return !status.equals(UserStatusType.EXPIRED);
    }

    @Override
    public boolean isAccountNonLocked() {
        return !status.equals(UserStatusType.LOCKED);
    }

    // disable for default
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status.equals(UserStatusType.ENABLE);
    }
}
