package thesis.api.position;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thesis.api.ThesisController;
import thesis.api.position.model.PositionsResponse;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping("/api/positions")
public class PositionsController extends ThesisController {

    @GetMapping
    public ResponseEntity<PositionsResponse> getPositions(
            @RequestHeader @NotNull UUID employeeId,
            @RequestParam(value="active", required = false, defaultValue = "true") Boolean active
    ){
        return ResponseEntity.ok().build();
    }
}
