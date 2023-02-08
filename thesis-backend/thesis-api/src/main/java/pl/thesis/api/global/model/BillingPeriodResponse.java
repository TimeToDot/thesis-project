package pl.thesis.api.global.model;

import lombok.Builder;

@Builder
public record BillingPeriodResponse(
        Integer id,
        String name
) {

}
