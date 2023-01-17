package thesis.domain.project.model;

import thesis.domain.employee.model.BillingPeriodDTO;

import java.util.UUID;

public record ProjectUpdatePayloadDTO(
        UUID projectId,
        UUID moderatorId,
        String name,
        String description,
        BillingPeriodDTO billingPeriod,
        String image,
        Integer overtimeModifier,
        Integer bonusModifier,
        Integer nightModifier,
        Integer holidayModifier,
        Boolean active
) {
}
