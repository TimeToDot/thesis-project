package thesis.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thesis.domain.customer.CustomerDto;

import java.util.List;
import java.util.UUID;

/**
 * <h1>MainController</h1>
 */

@RestController
@RequestMapping("/api/main")
public class MainController {



  @GetMapping(value = "/customers", produces = "application/json")
  @PreAuthorize("hasAuthority('CAN_READ')")
  public List<CustomerDto> getCustomerList(){
    var customers = getTempCustomers();

    return customers;
  }

  private List<CustomerDto> getTempCustomers(){
    var customerDto = CustomerDto.builder().customerId(UUID.randomUUID()).age(10).login("asd").name("dsa")
            .build();
    return List.of(customerDto);
  }
}