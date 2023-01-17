package thesis.domain.project.model;

import java.util.UUID;

public record ProjectUpdatePayloadDTO(
        UUID projectId,
        UUID moderatorId,
        String name,
        String description,
        String billingPeriod,
        String image,
        Integer overtimeModifier,
        Integer bonusModifier,
        Integer nightModifier,
        Integer holidayModifier,
        Boolean active
) {
}
