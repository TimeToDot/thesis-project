package thesis.domain.position.model;

import org.mapstruct.factory.Mappers;
import thesis.data.position.model.Position;

public interface PositionDTOMapper {


    PositionDTOMapper INSTANCE = Mappers.getMapper(PositionDTOMapper.class);

    PositionDTO mapPosition(Position position, Integer employeesCount);
}
