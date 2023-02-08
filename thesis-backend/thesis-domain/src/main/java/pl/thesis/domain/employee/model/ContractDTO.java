package pl.thesis.domain.employee.model;

import lombok.Builder;

@Builder
public record ContractDTO(
        Integer id,
        String name
) {
}
