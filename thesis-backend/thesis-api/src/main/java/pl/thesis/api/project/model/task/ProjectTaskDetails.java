package pl.thesis.api.project.model.task;

import lombok.Builder;

import java.util.Date;
import java.util.UUID;

@Builder
public record ProjectTaskDetails(
        String id,
        String projectId,
        String name,
        String description,
        Date creationDate,
        Date archiveDate,
        Boolean active
) {

}
