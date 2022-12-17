package thesis.security.services.model;

import java.util.UUID;

public record ProjectPrivilege(
        UUID id,
        String privilege
) {
}
