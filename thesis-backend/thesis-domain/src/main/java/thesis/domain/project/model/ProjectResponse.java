package thesis.domain.project.model;

import java.util.UUID;



public record ProjectResponse(
        UUID id,
        String name,
        String description,
        String billingPeriod,
        Integer wage ,
        Integer nightWage,
        Integer extraWage,
        String image,
        String moderatorId
){
}
