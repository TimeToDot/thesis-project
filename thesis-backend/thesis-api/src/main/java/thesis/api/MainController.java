package thesis.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thesis.domain.customer.Customer;

import java.util.List;
import java.util.UUID;

/**
 * <h1>MainController</h1>
 *
 * @author docz <a href="http://www.ailleron.com">AILLERON S.A.</a>
 * @version 1.0.0
 * @since TODO
 */

@RestController
@RequestMapping("/api/main")
public class MainController {



  @GetMapping(value = "/customers", produces = "application/json")
  public List<Customer> getCustomerList(){
    var customers = getTempCustomers();

    return customers;
  }

  private List<Customer> getTempCustomers(){
    return List.of(
      Customer.builder()
        .customerId(UUID.randomUUID())
        .name("first")
        .surname("f")
        .login("first")
        .age(5)
        .build()
    );
  }
}