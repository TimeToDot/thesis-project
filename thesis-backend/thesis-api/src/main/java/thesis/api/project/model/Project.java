package thesis.api.project.model;

import java.util.UUID;



public record Project (
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
