package thesis.domain.project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.util.Date;
import java.util.UUID;



@Builder
public record ProjectDTO(
        UUID id,
        String name,
        String description,
        String billingPeriod,
        String image,
        UUID moderatorId,
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
