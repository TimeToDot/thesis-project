package pl.thesis.api.project.model;

import pl.thesis.domain.employee.model.BillingPeriodDTO;

public record ProjectCreatePayload(
        String moderatorId,
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