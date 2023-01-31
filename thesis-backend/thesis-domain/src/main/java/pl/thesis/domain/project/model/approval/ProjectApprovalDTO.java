package pl.thesis.domain.project.model.approval;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import pl.thesis.domain.project.model.employee.EmployeeDTO;

import java.util.Date;
import java.util.UUID;

@Builder
public record ProjectApprovalDTO(
        UUID projectEmployeeId,
        EmployeeDTO employee,
        ApprovalStatus status,
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date lastRequest
) {
}
