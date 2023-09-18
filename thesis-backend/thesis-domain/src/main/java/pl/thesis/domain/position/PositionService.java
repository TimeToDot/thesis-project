package pl.thesis.domain.position;

import org.springframework.transaction.annotation.Transactional;
import pl.thesis.domain.paging.PagingSettings;
import pl.thesis.domain.position.model.PositionCreatePayloadDTO;
import pl.thesis.domain.position.model.PositionResponseDTO;
import pl.thesis.domain.position.model.PositionUpdatePayloadDTO;
import pl.thesis.domain.position.model.PositionsResponseDTO;

public interface PositionService {
    PositionResponseDTO getPosition(Long positionId);

    PositionsResponseDTO getPositions(PagingSettings settings, Boolean active);

    @Transactional
    Long updatePosition(PositionUpdatePayloadDTO payloadDTO);

    @Transactional
    Long addPosition(PositionCreatePayloadDTO payloadDTO);
}
