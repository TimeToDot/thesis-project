package pl.thesis.domain.project.model.approval;

import java.util.List;

public record ProjectApprovalTasksPayloadDTO(
        List<Long> tasks,
        Long projectId,
        Long accountProjectId
) {
}
