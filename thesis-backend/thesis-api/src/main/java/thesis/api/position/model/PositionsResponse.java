package thesis.api.position.model;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;



@AllArgsConstructor
@Builder
public class PositionsResponse{
    private final List<Position> positions;
}
