package thesis.domain.position;

import org.mapstruct.Mapper;
import thesis.data.position.model.Position;
import thesis.domain.employee.model.SimplePositionDTO;
import thesis.domain.mapper.MapStructConfig;

@Mapper(config = MapStructConfig.class)
public interface PositionMapper {

    SimplePositionDTO simpleMap(Position position);
}
