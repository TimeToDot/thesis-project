package pl.thesis.domain.position.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.util.Date;

@Builder
public record PositionResponseDTO(
        Long id,
        String name,
        String description,
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date creationDate,
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date archiveDate,
        Integer count,
        Boolean active
) {
}
