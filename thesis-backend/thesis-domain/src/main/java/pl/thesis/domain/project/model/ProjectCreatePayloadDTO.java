package pl.thesis.domain.project.model;

import pl.thesis.domain.employee.model.BillingPeriodDTO;

import java.util.UUID;

public record ProjectCreatePayloadDTO(
        UUID moderatorId,
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
