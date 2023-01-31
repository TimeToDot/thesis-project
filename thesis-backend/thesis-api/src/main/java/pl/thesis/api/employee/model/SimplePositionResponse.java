package pl.thesis.api.employee.model;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SimplePositionResponse(
        String id,
        String name
) {
}
