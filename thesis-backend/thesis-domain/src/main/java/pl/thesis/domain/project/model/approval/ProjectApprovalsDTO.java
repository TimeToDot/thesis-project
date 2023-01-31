package pl.thesis.domain.project.model.approval;

import lombok.Builder;
import pl.thesis.domain.paging.Paging;
import pl.thesis.domain.paging.Sorting;

import java.util.List;

@Builder
public record ProjectApprovalsDTO(
        List<ProjectApprovalDTO> employees,
        Paging paging,
        Sorting sorting
) {
}
