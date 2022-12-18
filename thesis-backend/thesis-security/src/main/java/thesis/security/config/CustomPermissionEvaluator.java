package thesis.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import thesis.data.project.model.Project;

import java.io.Serializable;
import java.util.UUID;

@Slf4j
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if ((authentication == null) || (targetDomainObject == null) || !(permission instanceof String authority)){
            return false;
        }
        UUID projectId = ((UUID) targetDomainObject);

        return hasPrivilege(authentication, projectId, authority);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        log.info("hasPermission");

        if (authentication == null
                || targetType == null
                || !(permission instanceof String authority)
                || !(targetId instanceof UUID projectId)) {
            return false;
        }

        return hasPrivilege(authentication, projectId, authority);
    }

    private boolean hasPrivilege(Authentication authentication, UUID projectId, String permission) {
        boolean isGranted = false;
        log.info("hasPrivilege, projectId: {},  permission: {}", projectId.toString(), permission);
        try{
            var auths = authentication
                    .getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .filter(auth -> auth.startsWith(projectId.toString()))
                    .toList();
            auths.forEach(s -> log.info("hasPrivilege auth: {}", s));

            isGranted = auths.stream().anyMatch(auth -> auth.contains(permission));
        } catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }
        log.info("isGranted: " + isGranted);

        return isGranted;
    }
}
