package pl.thesis.api.project.model.approval;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import pl.thesis.api.project.model.employee.EmployeeResponse;
import pl.thesis.domain.project.model.approval.ApprovalStatus;

import java.util.Date;

@Builder
public record ProjectApprovalResponse(
        String projectEmployeeId,
        EmployeeResponse employee,
        ApprovalStatus status,
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date lastRequest
) {
}
