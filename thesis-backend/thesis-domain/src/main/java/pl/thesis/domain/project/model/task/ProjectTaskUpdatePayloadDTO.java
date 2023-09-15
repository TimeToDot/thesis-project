package pl.thesis.domain.project.model.task;

public record ProjectTaskUpdatePayloadDTO(
        Long id,

        Long projectId,

        String name,
        String description,
        Boolean active
) {

}
