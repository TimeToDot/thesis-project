package pl.thesis.security.services.model;

import lombok.Builder;

@Builder
public record ContractSec(
        Integer id,
        String name
) {
}
