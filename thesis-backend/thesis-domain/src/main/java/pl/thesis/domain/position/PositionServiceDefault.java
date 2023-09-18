package pl.thesis.domain.position;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.thesis.data.position.PositionRepository;
import pl.thesis.data.position.model.Position;
import pl.thesis.data.position.model.PositionType;
import pl.thesis.domain.paging.PagingSettings;
import pl.thesis.domain.position.model.PositionCreatePayloadDTO;
import pl.thesis.domain.position.model.PositionResponseDTO;
import pl.thesis.domain.position.model.PositionUpdatePayloadDTO;
import pl.thesis.domain.position.model.PositionsResponseDTO;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static pl.thesis.domain.paging.PagingHelper.getPaging;
import static pl.thesis.domain.paging.PagingHelper.getSorting;

@AllArgsConstructor
@Service
public class PositionServiceDefault implements PositionService{

    private final PositionRepository positionRepository;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @PostConstruct
    private void init(){
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public PositionResponseDTO getPosition(Long positionId) {
        var position = positionRepository.findById(positionId).orElseThrow();

        return buildPositionDTO(position);
    }

    @Override
    public PositionsResponseDTO getPositions(PagingSettings settings, Boolean active) {
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

    @Override
    @Transactional
    public Long updatePosition(PositionUpdatePayloadDTO payloadDTO) {
        var position = positionRepository.findById(payloadDTO.id()).orElseThrow();

        if (Boolean.TRUE.equals(payloadDTO.active())) {
            position.setStatus(PositionType.ACTIVE);
        } else {
            position.setStatus(PositionType.INACTIVE);
            try {
                position.setArchiveDate(sdf.parse(sdf.format(new Date())));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        position.setName(payloadDTO.name());
        position.setDescription(position.getDescription());

        positionRepository.save(position);

        return position.getId();
    }

    @Override
    @Transactional
    public Long addPosition(PositionCreatePayloadDTO payloadDTO) {
        if (positionRepository.existsByName(payloadDTO.name())) {
            throw new RuntimeException("position with name : %s already exist!".formatted(payloadDTO.name()));
        }

        var position = new Position();
        position.setName(payloadDTO.name());
        position.setDescription(payloadDTO.description());
        position.setStatus(PositionType.ACTIVE);

        try {
            position.setCreationDate(sdf.parse(sdf.format(new Date())));
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
