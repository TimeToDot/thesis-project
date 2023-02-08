package pl.thesis.domain.project.model.approval;

import java.util.List;
import java.util.UUID;

public record ProjectApprovalTasksPayloadDTO(
        List<UUID> tasks,
        UUID projectId,
        UUID accountProjectId
) {
}
