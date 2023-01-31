package pl.thesis.api.employee.model.task;

import pl.thesis.domain.paging.PagingSettings;

import javax.validation.constraints.NotNull;
import java.util.Date;

public record EmployeeTasksRequest(

        @NotNull
        Date startDate,

        @NotNull
        Date endDate,

        PagingSettings settings
) {
}
