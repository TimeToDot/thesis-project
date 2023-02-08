package pl.thesis.domain.project.model.task;

import lombok.Builder;

import java.util.Date;
import java.util.UUID;

@Builder
public record ProjectTaskDetailsDTO(
        UUID id,
        UUID projectId,
        String name,
        String description,
        Date creationDate,
        Date archiveDate,
        Boolean active
) {

}
