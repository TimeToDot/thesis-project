package thesis.domain.customer;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.UUID;

/**

 */
@Value
@Builder
@EqualsAndHashCode
public class Customer {
  private final UUID customerId;

  private final String login;

  private final String name;

  private final String surname;

  private final Integer age;
}