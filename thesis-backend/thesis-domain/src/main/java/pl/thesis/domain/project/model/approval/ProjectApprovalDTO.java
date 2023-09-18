package pl.thesis.domain.project.model.approval;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import pl.thesis.domain.project.model.employee.EmployeeDTO;

import java.util.Date;

@Builder
public record ProjectApprovalDTO(
        Long projectEmployeeId,
        EmployeeDTO employee,
        ApprovalStatus status,
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date lastRequest
) {
}
