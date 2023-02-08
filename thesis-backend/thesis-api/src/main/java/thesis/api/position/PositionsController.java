package thesis.api.position;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thesis.api.position.model.PositionsResponse;

@RestController
@RequestMapping("/api/positions")
public class PositionsController {

    @GetMapping
    public ResponseEntity<PositionsResponse> getPositions(){
        return ResponseEntity.ok().build();
    }
}
