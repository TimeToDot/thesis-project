package pl.thesis.domain.position;

import org.mapstruct.Mapper;
import pl.thesis.data.position.model.Position;
import pl.thesis.domain.employee.model.SimplePositionDTO;
import pl.thesis.domain.mapper.MapStructConfig;
import pl.thesis.domain.position.model.PositionResponseDTO;

@Mapper(config = MapStructConfig.class)
public interface PositionMapperDTO {

    SimplePositionDTO simpleMap(Position position);
    PositionResponseDTO map(Position position);
}
