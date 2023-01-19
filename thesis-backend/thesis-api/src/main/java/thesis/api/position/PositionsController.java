package thesis.api.position;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import thesis.api.ThesisController;
import thesis.domain.position.PositionService;
import thesis.domain.position.model.PositionCreatePayloadDTO;
import thesis.domain.position.model.PositionResponseDTO;
import thesis.domain.position.model.PositionUpdatePayloadDTO;
import thesis.domain.position.model.PositionsResponseDTO;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/positions")
public class PositionsController extends ThesisController {

    private final PositionService positionService;

    @PreAuthorize("hasAuthority('CAN_READ')")
    @GetMapping
    public ResponseEntity<List<PositionResponseDTO>> getPositions(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestParam(value="active", required = false, defaultValue = "true") Boolean active,
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="size", required = false) Integer size,
            @RequestParam(value="direction", required = false) String direction,
            @RequestParam(value="key", required = false) String key
    ){
        var settings = initPaging(page, size, key, direction);

        var response = positionService.getPositions(settings, active);

        return ResponseEntity.ok(response.positions());
    }

    @PreAuthorize("hasAuthority('CAN_READ')")
    @GetMapping("/{positionId}")
    public ResponseEntity<PositionResponseDTO> getPosition(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @PathVariable UUID positionId

    ){
        var response = positionService.getPosition(positionId);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('CAN_ADMIN_POSITIONS')")
    @PostMapping
    public ResponseEntity<UUID> addPosition(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestBody PositionCreatePayloadDTO payload){
        var response = positionService.addPosition(payload);

        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasAuthority('CAN_ADMIN_POSITIONS')")
    @PutMapping("/{positionId}")
    public ResponseEntity<UUID> updatePosition(
            @RequestHeader(required = false) UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestBody PositionUpdatePayloadDTO payload
    ){
        var response = positionService.updatePosition(payload);

        return ResponseEntity.ok(response);
    }
}
