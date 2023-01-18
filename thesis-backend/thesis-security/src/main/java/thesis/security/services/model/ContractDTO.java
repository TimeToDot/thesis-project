package thesis.security.services.model;

import lombok.Builder;

@Builder
public record ContractDTO(
        Integer id,
        String name
) {
}
