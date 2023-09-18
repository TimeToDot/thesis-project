package pl.thesis.domain.project.model.task;

import lombok.Builder;

import java.util.Date;

@Builder
public record ProjectTaskDetailsDTO(
        Long id,
        Long projectId,
        String name,
        String description,
        Date creationDate,
        Date archiveDate,
        Boolean active
) {

}
