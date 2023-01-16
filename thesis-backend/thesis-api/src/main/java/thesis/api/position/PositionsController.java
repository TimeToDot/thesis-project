package thesis.api.position;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import thesis.api.ThesisController;
import thesis.domain.paging.PagingSettings;
import thesis.domain.position.PositionService;
import thesis.domain.position.model.PositionsResponseDTO;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/positions")
public class PositionsController extends ThesisController {

    private final PositionService positionService;

    @PreAuthorize("hasAuthority('CAN_READ')")
    @GetMapping
    public ResponseEntity<PositionsResponseDTO> getPositions(
            @RequestHeader UUID employeeId,
            @RequestHeader UUID projectId,
            @RequestParam(value="active", required = false, defaultValue = "true") Boolean active,
            @RequestParam(value="active", required = false) Integer page,
            @RequestParam(value="active", required = false) Integer size,
            @RequestParam(value="active", required = false) String direction,
            @RequestParam(value="active", required = false) String key
    ){
        var settings = initPagingSettings(page, size, key, direction);

        var response = positionService.getPositions(settings, active);

        return ResponseEntity.ok(response);
    }
}
