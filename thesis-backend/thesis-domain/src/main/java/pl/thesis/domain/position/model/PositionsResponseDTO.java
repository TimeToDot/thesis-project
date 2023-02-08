package pl.thesis.domain.position.model;

import lombok.Builder;
import pl.thesis.domain.paging.Paging;
import pl.thesis.domain.paging.Sorting;

import java.util.List;



@Builder
public record PositionsResponseDTO(
        List<PositionResponseDTO> positions,
        Paging paging,
        Sorting sorting
){

}
