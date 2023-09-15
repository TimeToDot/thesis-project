package pl.thesis.security.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import pl.thesis.security.services.model.ThesisId;

import java.io.Serializable;

@Slf4j
@AllArgsConstructor
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private final SymmetricEncryptor symmetricEncryptor;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if ((authentication == null) || (targetDomainObject == null) || !(permission instanceof String authority)){
            return false;
        }
        var encryptedProjectId = ((ThesisId) targetDomainObject).id().toString();
        var decryptedProjectId = symmetricEncryptor.decrypt(encryptedProjectId);
        var projectId = Long.parseLong(decryptedProjectId);

        return hasPrivilege(authentication, projectId, authority);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        log.info("hasPermission");

        if (authentication == null
                || targetType == null
                || !(permission instanceof String authority)
                || !(targetId instanceof Long projectId)) {
            return false;
        }

        return hasPrivilege(authentication, projectId, authority);
    }

    private boolean hasPrivilege(Authentication authentication, Long projectId, String permission) {
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
