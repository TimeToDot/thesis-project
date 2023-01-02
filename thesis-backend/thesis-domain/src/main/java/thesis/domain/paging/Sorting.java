package thesis.domain.paging;

import lombok.Builder;

@Builder
public record Sorting(
        String direction,
        String key
) {
}
