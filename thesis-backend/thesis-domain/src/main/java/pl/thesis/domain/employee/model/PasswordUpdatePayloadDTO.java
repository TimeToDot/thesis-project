package pl.thesis.domain.employee.model;

import java.util.UUID;

public record PasswordUpdatePayloadDTO(
        UUID id,
        String password
) {
}
