package pl.thesis.api.position.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.util.Date;
import java.util.UUID;

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
