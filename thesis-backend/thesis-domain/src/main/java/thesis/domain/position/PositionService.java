package thesis.domain.position;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thesis.data.position.PositionRepository;
import thesis.data.position.model.Position;
import thesis.data.position.model.PositionType;
import thesis.domain.paging.PagingSettings;
import thesis.domain.position.model.PositionCreatePayloadDTO;
import thesis.domain.position.model.PositionResponseDTO;
import thesis.domain.position.model.PositionUpdatePayloadDTO;
import thesis.domain.position.model.PositionsResponseDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static thesis.domain.paging.PagingHelper.getPaging;
import static thesis.domain.paging.PagingHelper.getSorting;

@AllArgsConstructor
@Service
public class PositionService {

    private final PositionRepository positionRepository;
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Transactional
    public PositionResponseDTO getPosition(UUID positionId) {
        var position = positionRepository.findById(positionId).orElseThrow();

        return buildPositionDTO(position);
    }

    public PositionsResponseDTO getPositions(PagingSettings settings, Boolean active){
        var status = Boolean.TRUE.equals(active) ? PositionType.ACTIVE : PositionType.INACTIVE;

        var positions = positionRepository.findAllByStatus(status, settings.getPageable());
        var dtoList = positions.stream().map(this::buildPositionDTO).toList();
        var paging = getPaging(settings, positions);
        var sorting = getSorting(settings);

        return PositionsResponseDTO.builder()
                .positions(dtoList)
                .paging(paging)
                .sorting(sorting)
                .build();
    }

    @Transactional
    public UUID updatePosition(PositionUpdatePayloadDTO payloadDTO){
        if (payloadDTO.name() != null && positionRepository.existsByName(payloadDTO.name())){
            throw new RuntimeException("position with name : %s already exist!".formatted(payloadDTO.name()));
        }

        var position = positionRepository.findById(payloadDTO.id()).orElseThrow();

        if (payloadDTO.active() != null){
            if (Boolean.TRUE.equals(payloadDTO.active())) {
                position.setStatus(PositionType.ACTIVE);
            } else {
                position.setStatus(PositionType.INACTIVE);
                try {
                    position.setArchiveDate(format.parse(format.format(new Date())));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (payloadDTO.name() != null){
            position.setName(payloadDTO.name());
        }
        if (payloadDTO.description() != null){
            position.setDescription(position.getDescription());
        }

        positionRepository.save(position);

        return position.getId();
    }

    @Transactional
    public UUID addPosition(PositionCreatePayloadDTO payloadDTO){
        if (positionRepository.existsByName(payloadDTO.name())){
            throw new RuntimeException("position with name : %s already exist!".formatted(payloadDTO.name()));
        }

        var position = new Position();
        position.setName(payloadDTO.name());
        position.setDescription(payloadDTO.description());
        position.setStatus(PositionType.ACTIVE);

        try {
            position.setCreationDate(format.parse(format.format(new Date())));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        positionRepository.save(position);

        return position.getId();
    }

    private PositionResponseDTO buildPositionDTO(Position position) {
        return PositionResponseDTO.builder()
                .id(position.getId())
                .name(position.getName())
                .description(position.getDescription())
                .creationDate(position.getCreationDate())
                .archiveDate(position.getArchiveDate())
                .active(position.getStatus().compareTo(PositionType.ACTIVE) == 0)
                .count(position.getAccounts().size())
                .build();
    }
}