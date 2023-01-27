package thesis.api.employee.model.project.temp;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ProjectTempResponse(
        UUID id,
        String name,
        String image,
        Boolean active
) {
}
