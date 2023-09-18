package pl.thesis.domain.project.model.task;

public record ProjectTaskCreatePayloadDTO(

        Long projectId,
        String name,
        String description
) {

}
