package thesis.api.project.model;

import java.util.UUID;

public record ProjectTask(
        UUID id,
        String name,
        String description,
        String creationDate
) {
}
