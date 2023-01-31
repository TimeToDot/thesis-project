package pl.thesis.api.employee.model;

import lombok.Builder;

@Builder
public record BillingPeriodResponse(
        Integer id,
        String name
) {

}
