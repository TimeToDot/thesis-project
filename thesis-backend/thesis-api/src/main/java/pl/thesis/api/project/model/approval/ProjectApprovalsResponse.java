package pl.thesis.api.project.model.approval;

import lombok.Builder;
import pl.thesis.domain.paging.Paging;
import pl.thesis.domain.paging.Sorting;

import java.util.List;

@Builder
public record ProjectApprovalsResponse(
        List<ProjectApprovalResponse> employees,
        Paging paging,
        Sorting sorting
) {
}
