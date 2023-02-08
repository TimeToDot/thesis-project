package pl.thesis.api.position.model;

import lombok.Builder;
import pl.thesis.domain.paging.Paging;
import pl.thesis.domain.paging.Sorting;

import java.util.List;



@Builder
public record PositionsResponse(
        List<PositionResponse> positions,
        Paging paging,
        Sorting sorting
){

}
