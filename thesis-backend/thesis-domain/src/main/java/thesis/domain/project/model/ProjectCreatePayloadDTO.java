package thesis.domain.project.model;

import thesis.domain.employee.model.BillingPeriodDTO;

import java.util.UUID;

public record ProjectCreatePayloadDTO(
        UUID moderatorId,
        String name,
        String description,
        String billingPeriod,
        String image,
        Integer overtimeModifier,
        Integer bonusModifier,
        Integer nightModifier,
        Integer holidayModifier
) {
}
