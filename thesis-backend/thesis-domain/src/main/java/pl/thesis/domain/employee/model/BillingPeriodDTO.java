package pl.thesis.domain.employee.model;

import lombok.Builder;

@Builder
public record BillingPeriodDTO(
        Integer id,
        String name
) {

}
