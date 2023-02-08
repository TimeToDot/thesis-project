package pl.thesis.api.project.model.task;

public record ProjectTaskUpdatePayload(
        String id,
        String name,
        String description,
        Boolean active
) {

}
