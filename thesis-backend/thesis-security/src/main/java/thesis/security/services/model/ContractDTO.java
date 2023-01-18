package thesis.security.services.model;

import lombok.Builder;

@Builder
public record ContractDTO(
        String id,
        ContractTypeDTO name
) {
}
