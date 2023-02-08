package pl.thesis.api.global.model;

import lombok.Builder;

@Builder
public record ContractResponse(
        Integer id,
        String name
) {
}
