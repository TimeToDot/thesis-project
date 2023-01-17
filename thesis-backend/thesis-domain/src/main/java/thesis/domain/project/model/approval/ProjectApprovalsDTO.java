package thesis.domain.project.model.approval;

import lombok.Builder;
import thesis.domain.paging.Paging;
import thesis.domain.paging.Sorting;

import java.util.List;

@Builder
public record ProjectApprovalsDTO(
        List<ProjectApprovalDTO> employees,
        Paging paging,
        Sorting sorting
) {
}
