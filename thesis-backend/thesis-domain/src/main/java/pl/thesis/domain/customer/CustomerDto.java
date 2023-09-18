package pl.thesis.domain.customer;

import lombok.Builder;

/**
DTO for tests
 */
@Builder
public record CustomerDto (
        Long customerId,
        String login,
        String name,
        String surname,
        Integer age) {

}