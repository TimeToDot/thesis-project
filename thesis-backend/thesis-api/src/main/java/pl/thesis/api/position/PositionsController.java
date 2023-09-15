package pl.thesis.api.position;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.thesis.api.ThesisController;
import pl.thesis.api.converter.IdConverter;
import pl.thesis.api.converter.model.ThesisId;
import pl.thesis.api.position.model.PositionCreatePayload;
import pl.thesis.api.position.model.PositionResponse;
import pl.thesis.api.position.model.PositionUpdatePayload;
import pl.thesis.domain.position.PositionService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/positions")
public class PositionsController extends ThesisController {

    private final PositionService positionService;
    private final PositionMapper positionsMapper;
    private final IdConverter converter;

    @PreAuthorize("hasAuthority('CAN_READ')")
    @GetMapping
    public ResponseEntity<List<PositionResponse>> getPositions(
            @RequestParam(value="active", required = false, defaultValue = "true") Boolean active,
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="size", required = false) Integer size,
            @RequestParam(value="direction", required = false) String direction,
            @RequestParam(value="key", required = false) String key
    ){
        var settings = initPaging(page, size, key, direction);

        var dto = positionService.getPositions(settings, active);
        var response = positionsMapper.mapToPositionsResponse(dto);

        return ResponseEntity.ok(response.positions());
    }

    @PreAuthorize("hasAuthority('CAN_READ')")
    @GetMapping("/{positionId}")
    public ResponseEntity<PositionResponse> getPosition(
            @PathVariable ThesisId positionId

    ){
        var dto = positionService.getPosition(positionId.id());
        var response = positionsMapper.mapToPositionResponse(dto);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('CAN_ADMIN_POSITIONS')")
    @PostMapping
    public ResponseEntity<Long> addPosition(
            @RequestBody PositionCreatePayload payload
    ){

        var dto = positionsMapper.mapToPositionCreatePayloadDto(payload);
        var response = positionService.addPosition(dto);

        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasAuthority('CAN_ADMIN_POSITIONS')")
    @PutMapping("/{positionId}")
    public ResponseEntity<String> updatePosition(
            @RequestBody PositionUpdatePayload payload,
            @PathVariable ThesisId positionId
    ){
        var dto = positionsMapper.mapToPositionUpdatePayloadDto(payload, positionId.id());
        var response = positionService.updatePosition(dto);

        return ResponseEntity.ok(converter.mapToText(response));
    }
}
