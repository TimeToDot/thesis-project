package thesis.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  @GetMapping("/")
  public String getMain(){
    return "siema";
  }
}