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

import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/position")
public class PositionController extends ThesisController {

    private final PositionService positionService;

    @PreAuthorize("hasAuthority('CAN_READ')")
    @GetMapping("/{positionId}")
    public ResponseEntity<PositionResponseDTO> getPosition(
            @RequestHeader UUID employeeId,
            @RequestHeader UUID projectId,
            @PathVariable UUID positionId

    ){
        var response = positionService.getPosition(positionId);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('CAN_ADMIN_POSITIONS')")
    @PostMapping
    public ResponseEntity<UUID> addPosition(
            @RequestHeader UUID employeeId,
            @RequestHeader UUID projectId,
            @RequestBody PositionCreatePayloadDTO payloadDTO){
        var response = positionService.addPosition(payloadDTO);

        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasAuthority('CAN_ADMIN_POSITIONS')")
    @PutMapping
    public ResponseEntity<UUID> updatePosition(
            @RequestHeader UUID employeeId,
            @RequestHeader UUID projectId,
            @RequestBody PositionUpdatePayloadDTO payloadDTO
    ){
        var response = positionService.updatePosition(payloadDTO);

        return ResponseEntity.ok(response);
    }
}
