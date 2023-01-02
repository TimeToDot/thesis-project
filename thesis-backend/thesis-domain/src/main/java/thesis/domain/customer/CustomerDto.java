package thesis.domain.customer;

import lombok.Builder;

import java.util.UUID;

/**

 */
@Builder
public record CustomerDto (
        UUID customerId,
        String login,
        String name,
        String surname,
        Integer age) {

}