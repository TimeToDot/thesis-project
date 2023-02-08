package pl.thesis.api.employee.model.project.temp;

import lombok.Builder;

@Builder
public record ProjectTempResponse(
        String id,
        String name,
        String image,
        Boolean active
) {
}
