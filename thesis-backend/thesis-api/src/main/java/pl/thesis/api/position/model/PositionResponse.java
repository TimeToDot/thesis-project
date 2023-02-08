package pl.thesis.api.position.model;

import lombok.Builder;

import java.util.Date;

@Builder
public record PositionResponse(
        String id,
        String name,
        String description,

        Date creationDate,

        Date archiveDate,
        Integer count,
        Boolean active
) {
}
