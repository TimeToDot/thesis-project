package pl.thesis.api.employee.model.project;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public record EmployeeProjectsToApprovePayload(

        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        Date startDate,
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        Date endDate,
        List<Long> projectIds
) {
}
