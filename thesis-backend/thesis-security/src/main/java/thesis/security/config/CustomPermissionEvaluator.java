package thesis.security.config;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        // TODO: 17/12/2022 develop needed
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        // TODO: 17/12/2022 develop needed
        return false;
    }

    private boolean hasPrivilege(Authentication auth, String targetType, String permission) {
        // TODO: 17/12/2022 develop needed
        for (GrantedAuthority grantedAuth : auth.getAuthorities()) {
            if (grantedAuth.getAuthority().startsWith(targetType) &&
                    grantedAuth.getAuthority().contains(permission)) {
                return true;
            }
        }
        return false;
    }
}
