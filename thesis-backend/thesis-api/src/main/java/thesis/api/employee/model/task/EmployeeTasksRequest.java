package thesis.api.employee.model.task;

import thesis.domain.paging.PagingSettings;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

public record EmployeeTasksRequest(

        @NotNull
        Date startDate,

        @NotNull
        Date endDate,

        PagingSettings settings
) {
}
