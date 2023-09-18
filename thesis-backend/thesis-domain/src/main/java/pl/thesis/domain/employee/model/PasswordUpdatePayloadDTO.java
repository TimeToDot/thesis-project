package pl.thesis.domain.employee.model;

public record PasswordUpdatePayloadDTO(
        Long id,
        String password
) {
}
