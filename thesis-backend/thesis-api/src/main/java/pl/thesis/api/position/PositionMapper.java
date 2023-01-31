package pl.thesis.api.position;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.thesis.api.converter.UuidConverter;
import pl.thesis.api.employee.model.SimplePositionResponse;
import pl.thesis.api.position.model.PositionCreatePayload;
import pl.thesis.api.position.model.PositionResponse;
import pl.thesis.api.position.model.PositionUpdatePayload;
import pl.thesis.api.position.model.PositionsResponse;
import pl.thesis.domain.employee.model.SimplePositionDTO;
import pl.thesis.domain.mapper.MapStructConfig;
import pl.thesis.domain.position.model.PositionCreatePayloadDTO;
import pl.thesis.domain.position.model.PositionResponseDTO;
import pl.thesis.domain.position.model.PositionUpdatePayloadDTO;
import pl.thesis.domain.position.model.PositionsResponseDTO;

import java.util.UUID;

@Mapper(config = MapStructConfig.class, uses = UuidConverter.class)
public interface PositionMapper {

    PositionMapper INSTANCE = Mappers.getMapper(PositionMapper.class);

    @Mapping(target = "id", source = "id", qualifiedByName = {"mapToText"})
    SimplePositionResponse mapToSimplePositionResponse(SimplePositionDTO dto);

    PositionCreatePayloadDTO mapToPositionCreatePayloadDto(PositionCreatePayload payload);
    @Mapping(target = "id", source = "positionId", qualifiedByName = {"mapToId"})
    PositionUpdatePayloadDTO mapToPositionUpdatePayloadDto(PositionUpdatePayload payload, String positionId);
    @Mapping(target = "id", source = "id", qualifiedByName = {"mapToText"})
    PositionResponse mapToPositionResponse(PositionResponseDTO dto);

    @Mapping(target = "id", source = "id", qualifiedByName = {"mapToId"})
    PositionResponseDTO mapToPositionResponseDTO(PositionResponse response);

    PositionsResponse mapToPositionsResponse(PositionsResponseDTO dto);

}