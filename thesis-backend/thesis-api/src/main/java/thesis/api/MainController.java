package thesis.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

@Slf4j
@RestController
@RequestMapping("/api/main")
public class MainController {



  @GetMapping(value = "/info", produces = "application/json")
  @PreAuthorize("hasAuthority('CAN_READ')")
  public ResponseEntity<String> getCustomerList(){

    return ResponseEntity.ok(getInfo());
  }

  private String getInfo(){
    var description = "This is backend Thesis project";
    log.info(description);

    return description;
  }
}