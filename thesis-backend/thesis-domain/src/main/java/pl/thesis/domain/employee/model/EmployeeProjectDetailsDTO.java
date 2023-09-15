package pl.thesis.domain.employee.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record EmployeeProjectDetailsDTO(
        Long id,
        String name,
        String imagePath,
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date joinDate,
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date exitDate,
        Long projectEmployeeId,
        Boolean active
) {
}
