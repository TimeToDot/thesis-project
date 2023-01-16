package thesis.domain.position.model;

import lombok.Builder;
import thesis.domain.paging.Paging;
import thesis.domain.paging.Sorting;

import java.util.List;



@Builder
public record PositionsResponseDTO(
        List<PositionResponseDTO> positions,
        Paging paging,
        Sorting sorting
){

}
