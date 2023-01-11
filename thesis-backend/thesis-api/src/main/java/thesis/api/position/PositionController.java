package thesis.api.position;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thesis.api.ThesisController;
import thesis.api.position.model.PositionResponse;

@RestController
@RequestMapping("/api/position")
public class PositionController extends ThesisController {
    
    @GetMapping
    public ResponseEntity<PositionResponse> getPosition(){
        // TODO: 30/12/2022
        return null;
    }

    @PostMapping
    public ResponseEntity<PositionResponse> addPosition(){
        // TODO: 30/12/2022
        return null;
    }


    @PutMapping
    public ResponseEntity<PositionResponse> updatePosition(){
        // TODO: 30/12/2022
        return null;
    }
}
