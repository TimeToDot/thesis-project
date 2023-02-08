package pl.thesis.api.employee.model;

import lombok.Builder;

@Builder
public record SimplePositionResponse(
        String id,
        String name
) {
}
