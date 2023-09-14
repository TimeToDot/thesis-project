package pl.thesis.api.project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import pl.thesis.domain.employee.model.BillingPeriodDTO;

import java.util.Date;


@Builder
public record ProjectResponse(
        String id,
        String name,
        String description,
        BillingPeriodDTO billingPeriod,
        String image,
        String moderatorId,
        Integer employeesCount,
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date creationDate,
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date archiveDate,
        Integer overtimeModifier,
        Integer bonusModifier,
        Integer nightModifier,
        Integer holidayModifier,
        Boolean active
){
}