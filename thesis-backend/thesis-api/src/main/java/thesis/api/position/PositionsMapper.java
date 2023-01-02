package thesis.api.position;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import thesis.domain.mapper.MapStructConfig;

@Mapper(config = MapStructConfig.class)
public interface PositionsMapper {

    PositionsMapper INSTANCE = Mappers.getMapper(PositionsMapper.class);

}