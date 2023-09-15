package pl.thesis.domain.project.model;

import pl.thesis.domain.employee.model.BillingPeriodDTO;

public record ProjectCreatePayloadDTO(
        Long moderatorId,
        String name,
        String description,
        BillingPeriodDTO billingPeriod,
        String image,
        Integer overtimeModifier,
        Integer bonusModifier,
        Integer nightModifier,
        Integer holidayModifier
) {
}
