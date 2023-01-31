package pl.thesis.domain.employee.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;

import java.util.Arrays;

@Builder
public record BillingPeriodDTO(
        Integer id,
        String name
) {

}
