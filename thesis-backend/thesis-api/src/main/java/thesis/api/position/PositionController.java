package thesis.api.position;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import thesis.api.ThesisController;
import thesis.domain.position.PositionService;
import thesis.domain.position.model.PositionCreatePayloadDTO;
import thesis.domain.position.model.PositionResponseDTO;
import thesis.domain.position.model.PositionUpdatePayloadDTO;

import java.util.UUID;

@Hidden
@AllArgsConstructor
@RestController
@RequestMapping("/api/position")
public class PositionController extends ThesisController {

    private final PositionService positionService;

    @PreAuthorize("hasAuthority('CAN_READ')")
    @GetMapping("/{positionId}")
    public ResponseEntity<PositionResponseDTO> getPosition(
            @RequestHeader UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @PathVariable UUID positionId

    ){
        var response = positionService.getPosition(positionId);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('CAN_ADMIN_POSITIONS')")
    @PostMapping
    public ResponseEntity<UUID> addPosition(
            @RequestHeader UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestBody PositionCreatePayloadDTO payload){
        var response = positionService.addPosition(payload);

        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasAuthority('CAN_ADMIN_POSITIONS')")
    @PutMapping
    public ResponseEntity<UUID> updatePosition(
            @RequestHeader UUID employeeId,
            @RequestHeader(required = false) UUID projectId,
            @RequestBody PositionUpdatePayloadDTO payload
    ){
        var response = positionService.updatePosition(payload);

        return ResponseEntity.ok(response);
    }
}
