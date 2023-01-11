package thesis.api.position.model;

import lombok.Builder;
import thesis.domain.paging.Paging;
import thesis.domain.paging.Sorting;

import java.util.List;



@Builder
public record PositionsResponse(
        List<PositionResponse> positions,
        Paging paging,
        Sorting sorting
){

}
